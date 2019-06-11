clean_db_server:
	make _clean_db database=buguri
	make _clean_db database=buguri_test
	-psql -h localhost -U $(su) -d postgres -c 'drop role buguri';

_clean_db:
	-psql -h localhost -U $(su) -d postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '$(database)' AND pid <> pg_backend_pid()"
	-psql -h localhost -U $(su) -d postgres -c 'drop database $(database)';

_build_db:
	-psql -h localhost -U $(su) -d postgres -c "create user buguri with password 'password' createrole";
	psql -h localhost -U $(su) -d postgres -c 'create database $(database) with owner buguri';
	-psql -h localhost -U $(su) -d $(database) -c 'create extension if not exists "uuid-ossp"';

clean_testdb:
	make _clean_db database=buguri_test

build_db: ## Creates new empty database
	make _build_db database=buguri

build_testdb: ## Creates new empty database of test database
	make _build_db database=buguri_test

rebuild_testdb: clean_testdb build_testdb ## clean + build test db