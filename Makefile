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