FROM openjdk:24-ea-jdk-slim

WORKDIR /app


COPY target/*.jar app.jar


COPY src/main/resources/application.yml ./config/
COPY src/main/resources/application-local.yml ./config/

EXPOSE 8082


CMD ["sh", "-c", "java -jar app.jar --spring.config.location=classpath:/,file:./config/ --spring.profiles.active=${ACTIVE_PROFILE:-}"]
