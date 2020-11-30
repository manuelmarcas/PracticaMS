FROM openjdk:latest

WORKDIR /cliente
COPY ./target/cliente-0.0.1-SNAPSHOT.jar /cliente

EXPOSE 8080
CMD ["java", "-jar", "cliente-0.0.1-SNAPSHOT.jar"]