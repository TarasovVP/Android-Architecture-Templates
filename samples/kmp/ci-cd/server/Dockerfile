FROM openjdk:17-jdk-slim

WORKDIR /app

COPY server/build/libs/ktor-server-1.0.0.jar /app/ktor-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ktor-server.jar"]
