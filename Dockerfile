FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ticketing.jar
ENTRYPOINT ["java","-jar","/ticketing.jar"]
