
FROM openjdk:17-alpine
WORKDIR /app
COPY target/customer-ms-0.0.1-SNAPSHOT.jar customer-ms-0.0.1-SNAPSHOT.jar
EXPOSE 8585
ENTRYPOINT ["java", "-jar", "customer-ms-0.0.1-SNAPSHOT.jar"]
