#!/usr/bin/env bash
set -e

if [[ $(id -u) -ne 0 ]] ; then echo "root/sudo privileges required" ; exit 1 ; fi

echo
apt install -y certbot

read -p "DOMAIN_NAME=" DOMAIN_NAME

certbot certonly --standalone --preferred-challenges http --http-01-port 3001 -d ${DOMAIN_NAME}

cd /etc/letsencrypt/live/${DOMAIN_NAME}

read -p "KEYSTORE_PASS=" KEYSTORE_PASS
read -p "METABASE_DIR=" METABASE_DIR

openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 \
    -passin pass:${KEYSTORE_PASS} -passout pass:${KEYSTORE_PASS} \
    -name metabase -CAfile chain.pem -caname root

keytool -importkeystore -deststorepass ${KEYSTORE_PASS} -destkeypass ${KEYSTORE_PASS} \
    -destkeystore ${METABASE_DIR}/keystore.jks \
    -srckeystore /etc/letsencrypt/live/${DOMAIN_NAME}/keystore.p12 \
    -srcstoretype PKCS12 \
    -srcstorepass ${KEYSTORE_PASS} -alias metabase -noprompt

chown app: ${METABASE_DIR}/keystore.jks
chmod 664 ${METABASE_DIR}/keystore.jks


# setup renewal hooks

sed -e "s;%DOMAIN_NAME%;${DOMAIN_NAME};g" -e "s;%METABASE_DIR%;${METABASE_DIR};g" \
-e "s;%KEYSTORE_PASS%;${KEYSTORE_PASS};g" export-keystore.sh.template > /etc/letsencrypt/renewal-hooks/deploy/export-keystore.sh

chmod 755 /etc/letsencrypt/renewal-hooks/deploy/export-keystore.sh


PRE_HOOK_FILE=/etc/letsencrypt/renewal-hooks/pre/stop-metabase
echo '#!/bin/bash
systemctl stop metabase
' > ${PRE_HOOK_FILE}
chmod 755 ${PRE_HOOK_FILE}


POST_HOOK_FILE=/etc/letsencrypt/renewal-hooks/post/start-metabase
echo '#!/bin/bash
systemctl start metabase
' > ${POST_HOOK_FILE}
chmod 755 ${POST_HOOK_FILE}
