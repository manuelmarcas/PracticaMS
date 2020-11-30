FROM openjdk:latest

WORKDIR /visita
COPY ./target/visita-0.0.1-SNAPSHOT.jar /visita

EXPOSE 8761
CMD ["java", "-jar", "visita-0.0.1-SNAPSHOT.jar"]