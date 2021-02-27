# Course Management System

## Introduction
* An honest attempt to build a course management system
* CMS will consist of a client, server and a message broker
* 3 client interfaces:
    * Student
    * Instructor
    * Admin
* Server - REST API (Spring)
* On demand communication b/w client & server - HTTP/TCP
* Real time communication - JMS (Kafka/RabbitMQ/ActiveMQ)

## Project Setup

### Github token to access packages
Course Management System has dependencies 
on many javafx libraries *(maven dependencies)* as well as 
a *shared* `core` library (developed as part of this project) which is used both by the 
client module and the server module.

Since the shared `core` library is hosted as a Github Package,
you'll need to setup a Github token with your Github account.

Navigate to `GitHub > Settings > Developer Settings > Personal access`
and click on  `Generate New Token`

Give **at-least** `read:packages` permission.

Copy the token and setup the following environment variables:

```zsh
export GITHUB_USERNAME={Your Github Username}
export GITHUB_TOKEN={Copied token from the above step}
export JAVA_EXEC={Path to java 15.0.1 executable}
```

Make sure that the names of environment variables are as it is. 
Otherwise, you might face a tough time :P

## Running the project

### Using Maven
Just a simple command is enough to launch the JavaFX Application
```zsh
make 
```

### Using IntelliJ
I know a lot of folks love IntelliJ (including me), therefore
it makes sense to setup Intellij with the JavaFX distribution.

[This link](https://openjfx.io/openjfx-docs/) conveys information to setup JavaFX via various configurations.
Go to `JavaFX and IntelliJ` > `Non-modular from IDE` to setup JavaFX on IntelliJ.