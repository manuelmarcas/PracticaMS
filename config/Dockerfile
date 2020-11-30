FROM openjdk:latest

WORKDIR /configserver
COPY ./target/config-0.0.1-SNAPSHOT.jar /configserver

EXPOSE 8888
CMD ["java", "-jar", "config-0.0.1-SNAPSHOT.jar"]