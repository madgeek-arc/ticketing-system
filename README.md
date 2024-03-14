# Ticketing Service

## Build

### Requirements
- Java 11
- Maven

### Build instructions
Use maven to build the project.
```bash
mvn clean package
```
The output is a "jar" file located under `./target/*.jar`


## Deploy

### Dependencies
- MongoDB

### Deployment Instructions

#### 1. Create Properties file
Create an `application.yml` file and fill in the following properties.
```yml

spring:

  data.mongodb:
    host: localhost
    port: 27017
    database: dev

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri:
```

#### 2. Run Software

To deploy the software run:
```bash
java -jar <ticketing.jar> --spring.config.location=/path/to/application.yml
```
