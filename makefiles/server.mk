start_server: build_server
	test -f .env.local && source .env.local; java -jar build/libs/dwcc-server.jar

debug_server: build_server
	test -f .env.local && source .env.local; java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar build/libs/dwcc-server.jar

build_server: ## Builds the jar file
	./gradlew clean build -x test

test_server: rebuild_testdb ## Run tests
	GRADLE_OPTS="-Xmx256m" ./gradlew clean test

open_test_results:
	$(call _open,build/reports/tests/test/index.html)

start_server_wo_gradle:
	java -jar build/libs/dwcc-server.jar
