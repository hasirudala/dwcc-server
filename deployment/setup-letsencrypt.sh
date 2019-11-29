#!/usr/bin/env bash
set -e

if [[ $(id -u) -ne 0 ]] ; then echo "root/sudo privileges required" ; exit 1 ; fi

echo
apt install certbot
read -p "DOMAIN_NAME=" DOMAIN_NAME
read -p "WEBROOT=" WEBROOT
certbot certonly --webroot -w ${WEBROOT} -d ${DOMAIN_NAME}
cd /etc/letsencrypt/live/${DOMAIN_NAME}
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out ${WEBROOT}/../server/keystore.p12 -name tomcat -CAfile chain.pem -caname root
chown app: ${WEBROOT}/../server/keystore.p12
chmod 664 ${WEBROOT}/../server/keystore.p12
