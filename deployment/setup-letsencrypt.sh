#!/usr/bin/env bash
set -e

if [[ $(id -u) -ne 0 ]] ; then echo "root/sudo privileges required" ; exit 1 ; fi

echo
apt install certbot
read -p "DOMAIN_NAME=" DOMAIN_NAME
APP_ROOT=/home/app/dwcc-live
certbot certonly --standalone --preferred-challenges http -d ${DOMAIN_NAME}
cd /etc/letsencrypt/live/${DOMAIN_NAME}
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out ${APP_ROOT}/server/keystore.p12 -name tomcat -CAfile chain.pem -caname root
chown app: ${APP_ROOT}/server/keystore.p12
chmod 664 ${APP_ROOT}/server/keystore.p12
