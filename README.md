# Telephony WS

## Important

This project is a template for academic purposes, for a better project template, use one of the industry recomended frameworks:

- [Quarkus](https://quarkus.io/)
- [Spring](https://spring.io/)
- [Micronaut](https://micronaut.io/)
- [MicroProfile](https://microprofile.io/)
- [Dropwizard](https://www.dropwizard.io/): It's not update as often as the other frameworks and does not have all the fancy features
  that the others framework have (like DI, build-in integration with most of the modern cloud ecosystems).
- [Ktor](https://ktor.io/): for Kotlin fans

## What

This is a template for creating a Web REST API using Java, Jetty, Jersey only.

## Context

This is a structure that does not depend on Dependency Injection and instead rely on managing the creation of the
project. This is a small, but complete, software stack to be used as a start point for a University project. Test
coverage is minimal to encourage students to explore how to test. The exercise is to understand how everything works
together before using Dependency Injection frameworks.

## How to run

* You can manage your java versions manually by installing the desired version and setting the `JAVA_HOME` environment variable. We
  recommend using [SDKMAN](https://sdkman.io/) to manage your Java versions since it will configure it properly and will make managing
  multiple java versions and JVM related frameworks very easy.
* If Java and Maven are installed and configured, execute `mvn compile` to compile the code then `mvn exec:java` to run the application
* You have multiple verification that you can use:
    * Run unit tests with `mvn test`
    * Run all tests (Unit and integration) with `mvn integration-test`
    * Run all tests and build the application artifact (available in `target/TelephonyWs-0.0.1-SNAPSHOT.jar`) with `mvn verify`
* Here is an example of a command that will start with a clean build, compile, run all available tests (unit and integration) then start
  the application in one command `mvn clean compile verify exec:java`
* In an IDE, run as a Java Application the class `TelephonyWsMain`

### Made using:

* https://www.sitepoint.com/creating-crud-app-minutes-angulars-resource/
* http://www.freefavicon.com/
