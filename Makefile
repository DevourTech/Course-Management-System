.DEFAULT_GOAL := run

.PHONY: build
build:
	@echo "Installing dependencies...."
	@mvn clean install

.PHONY: run
run:
	@echo "Running Course Management System...."
	@mvn clean javafx:run