FROM openjdk:latest

WORKDIR /admin
COPY ./target/admin-0.0.1-SNAPSHOT.jar /admin

EXPOSE 8091
CMD ["java", "-jar", "admin-0.0.1-SNAPSHOT.jar"]