FROM openjdk:latest

WORKDIR /visita
COPY ./target/visita-0.0.1-SNAPSHOT.jar /visita

EXPOSE 8081
CMD ["java", "-jar", "visita-0.0.1-SNAPSHOT.jar"]