.DEFAULT_GOAL := run

.PHONY: build
build: core
	@echo "Installing dependencies...."
	@mvn clean install

.PHONY: run
run: build
	@echo "Running Course Management System...."
	@mvn clean javafx:run

.PHONY: core
core:
	@echo "Deploying cms core to local maven repository"
	@mvn clean install -f core/pom.xml

.PHONY: server
server: core
	@echo "Starting Spring Boot Application"
	@mvn spring-boot:run -f server/pom.xml

.PHONY: test
test:
	@echo "Running all the tests of Course Management System"
	@mvn test
	@mvn test -f core/pom.xml
	@mvn test -f server/pom.xml