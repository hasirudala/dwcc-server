start_server: build_server
	java -jar build/libs/buguri-server.jar

debug_server: build_server
	java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar build/libs/buguri-server.jar

build_server: ## Builds the jar file
	./gradlew clean build -x test

test_server: rebuild_testdb ## Run tests
	GRADLE_OPTS="-Xmx256m" ./gradlew clean test

start_server_wo_gradle:
	java -jar build/libs/buguri-server.jar
