#!/usr/bin/env bash
set -e

if [[ $(id -u) -ne 0 ]] ; then echo "root/sudo privileges required" ; exit 1 ; fi

echo
apt install certbot
read -p "DOMAIN_NAME=" DOMAIN_NAME
certbot certonly --standalone -d ${DOMAIN_NAME}
cd /etc/letsencrypt/live/${DOMAIN_NAME}
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 \
    -name metabase -CAfile chain.pem -caname root

read -p "PASSWORD=" PASSWORD
read -p "METABASE_DIR=" METABASE_DIR
keytool -importkeystore -deststorepass ${PASSWORD} -destkeypass ${PASSWORD} \
    -destkeystore ${METABASE_DIR}/keystore.jks \
    -srckeystore /etc/letsencrypt/live/${DOMAIN_NAME}/keystore.p12 \
    -srcstoretype PKCS12 \
    -srcstorepass ${PASSWORD} -alias tomcat

chown app: ${METABASE_DIR}/keystore.jks
chmod 664 ${METABASE_DIR}/keystore.jks
