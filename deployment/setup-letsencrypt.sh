#!/usr/bin/env bash
set -e

if [[ $(id -u) -ne 0 ]] ; then echo "root/sudo privileges required" ; exit 1 ; fi

echo
apt install -y certbot
echo
read -p "DOMAIN_NAME=" DOMAIN_NAME
APP_ROOT=/home/app/dwcc-live

certbot certonly --standalone --preferred-challenges http -d ${DOMAIN_NAME}

cd /etc/letsencrypt/live/${DOMAIN_NAME}

read -p "KEYSTORE_PASS=" KEYSTORE_PASS

openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem \
-passin pass:${KEYSTORE_PASS} -passout pass:${KEYSTORE_PASS} \
-out ${APP_ROOT}/server/keystore.p12 -name tomcat \
-CAfile chain.pem -caname root

chown app: ${APP_ROOT}/server/keystore.p12
chmod 664 ${APP_ROOT}/server/keystore.p12

# setup renewal hooks
sed -e "s;%DOMAIN_NAME%;${DOMAIN_NAME};g" -e "s;%KEYSTORE_PASS%;${KEYSTORE_PASS};g" \
-e "s;%APP_ROOT%;${APP_ROOT};g" export-keystore.sh.template > /etc/letsencrypt/renewal-hooks/deploy/export-keystore.sh
chmod 755 /etc/letsencrypt/renewal-hooks/deploy/export-keystore.sh


PRE_HOOK_FILE=/etc/letsencrypt/renewal-hooks/pre/stop-dwcc-server
echo '#!/bin/bash
systemctl stop dwcc-server
' > ${PRE_HOOK_FILE}
chmod 755 ${PRE_HOOK_FILE}


POST_HOOK_FILE=/etc/letsencrypt/renewal-hooks/post/start-dwcc-server
echo '#!/bin/bash
systemctl start dwcc-server
' > ${POST_HOOK_FILE}
chmod 755 ${POST_HOOK_FILE}
