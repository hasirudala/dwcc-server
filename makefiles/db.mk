clean_db_server:
	make _clean_db database=dwcc
	make _clean_db database=dwcc_test
	-psql -h localhost -U $(su) -d postgres -c 'drop role dwcc';

_clean_db:
	-psql -h localhost -U $(su) -d postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '$(database)' AND pid <> pg_backend_pid()"
	-psql -h localhost -U $(su) -d postgres -c 'drop database $(database)';

_build_db:
	-psql -h localhost -U $(su) -d postgres -c "create user dwcc with password 'password' createrole";
	psql -h localhost -U $(su) -d postgres -c 'create database $(database) with owner dwcc';
	-psql -h localhost -U $(su) -d $(database) -c 'create extension if not exists "uuid-ossp"';

clean_testdb:
	make _clean_db database=dwcc_test

build_db: ## Creates new empty database
	make _build_db database=dwcc

build_testdb: ## Creates new empty database of test database
	make _build_db database=dwcc_test

rebuild_testdb: clean_testdb build_testdb ## clean + build test db