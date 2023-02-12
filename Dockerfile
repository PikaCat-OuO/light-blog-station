FROM openjdk:latest

COPY *.jar /app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app.jar"]