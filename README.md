# Testing Java Microservices

https://www.manning.com/books/testing-java-microservices

## 1. Description

This project is a testbed to prototyping tests against micro-services using various technologies.

Be aware that some of the tests are designed to block for the demo - You will need to add breakpoints to break out.

## Cleanup
Sometimes you might need to cleanup

```
docker-compose -f service/docker-compose.yml down
```

## 2. Build and Run

### Build Environment

* Linux, Windows or MacOS
* JDK 1.8.x or newer
* Maven 3.5.2 or newer
* Docker 17.12 or newer
* Docker Compose 1.21 or newer

### Build Project

  `mvn [clean] verify`
