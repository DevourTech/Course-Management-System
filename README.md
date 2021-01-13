# Course Management System

## Introduction
* An honest attempt to build a course management system
* CMS will consist of a client, server and a message broker
* 3 client interfaces:
    * Student
    * Instructor
    * Admin
* Server - REST API 
* On demand communication b/w client & server - HTTP/TCP
* Real time communication - JMS (Kafka/RabbitMQ/ActiveMQ)

## How to run the project?

### [Manual] Maven in IntelliJ IDEA
* Install Maven on your local system
* Run a maven sync up on the root directory to install all the dependencies
* To compile, navigate to Maven (rightmost menu option) -> {Project Name} -> Plugins -> compiler -> `compile` target
* To run, navigate to Maven -> {Project Name} -> Plugins -> javafx -> `run` target

### [Manual] Without any build system in IntelliJ IDEA
* Install a JavaFX distribution and extract it in a particular path
* Add vm-options to run configurations while executing JavaFX applications
* Follow [this](https://openjfx.io/openjfx-docs/#install-javafx) link for more details

### Terminal

#### Download dependencies
```zsh
make build
```

#### Run the application
```zsh
make 
```
#### More to follow...