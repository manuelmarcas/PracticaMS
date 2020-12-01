FROM openjdk:latest

WORKDIR /factura
COPY ./target/factura-0.0.1-SNAPSHOT.jar /factura

EXPOSE 8083
CMD ["java", "-jar", "factura-0.0.1-SNAPSHOT.jar"]