.DEFAULT_GOAL := run

.PHONY: build
build:
	@echo "Installing dependencies...."
	@mvn install -s settings.xml

.PHONY: run
run: build
	@echo "Running Course Management System...."
	@mvn javafx:run

.PHONY: test
test: build
	@echo "Running all the tests of Course Management System"
	@mvn test

.PHONY: fmt
fmt:
	prettier --write "**/*.java"

.PHONY: install-formatter
install-formatter:
	@npm list --global prettier || npm install -g prettier prettier-plugin-java
	@echo "In an event of a permission error from npm, run sudo make install-formatter"