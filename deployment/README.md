### Setup HOWTO

1. Copy all files in `deployment` folder to, say, login user's `$HOME`
2. `./setup-server.sh`
3. `./setup-letsencrypt.sh`
4. `cd <path-to-app-root>/server`
5. Rename `env.local.template` to `.env.local` and set all environment variables

### App Server management
sudo systemctl start dwcc-server
sudo journalctl -f -u dwcc-server.service (console log) or tail -f /var/log/dwcc/dwcc.log.0
certbot is installed via apt-get (use certbot command for certificates management)
