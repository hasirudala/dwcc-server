### Setup HOWTO

1. Copy all files in `deployment` folder to, say, login user's `$HOME`
2. `./setup-server.sh`
3. `./setup-letsencrypt.sh`
4. `cd <path-to-app-root>/server`
5. Rename `env.local.template` to `.env.local` and set all environment variables
