#!/usr/bin/env bash
set -e

if [[ $(id -u) -ne 0 ]] ; then echo "root/sudo privileges required" ; exit 1 ; fi

apt-get update
apt upgrade -y
timedatectl set-timezone Asia/Kolkata

echo
echo "------------------------------------"
echo
echo "Adding user 'app'..."
addgroup app && adduser --system --ingroup app --home /home/app --shell /bin/bash app
usermod -aG www-data app
echo 'app ALL=(ALL)  NOPASSWD:ALL' | EDITOR='tee -a' visudo
echo
echo "------------------------------------"
echo
echo "Setup directory to serve production files..."
mkdir -p /home/app/dwcc-live/server/new
mkdir -p /home/app/dwcc-live/web/new
cp ./env.local.template /home/app/dwcc-live/server/.
echo
echo "------------------------------------"
echo
echo "Installing JAVA..."
apt install -y openjdk-11-jre-headless
setcap 'cap_net_bind_service+eip' /usr/lib/jvm/java-11-openjdk-amd64/bin/java
echo
echo "------------------------------------"
echo
echo "Installing tools..."
apt install -y postgresql-client
apt install -y awscli
echo "------------------------------------"
echo
cp ./dwcc-server.service /etc/systemd/system/.
systemctl daemon-reload
aws configure
ln -s /usr/bin/python3 /usr/local/bin/python