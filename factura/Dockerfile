FROM openjdk:latest

WORKDIR /factura
COPY ./target/factura-0.0.1-SNAPSHOT.jar /factura

EXPOSE 8082
CMD ["java", "-jar", "factura-0.0.1-SNAPSHOT.jar"]