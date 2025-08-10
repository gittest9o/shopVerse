FROM openjdk:24-ea-jdk-slim
WORKDIR /app

# Копируем JAR и конфигурационные файлы
COPY target/*.jar app.jar
COPY src/main/resources/application.yml ./config/
COPY src/main/resources/application-local.yml ./config/

# Открываем порт сервиса
EXPOSE 8084

# Запускаем приложение с возможностью выбора профиля
CMD ["sh", "-c", "java -jar app.jar --spring.config.location=classpath:/,file:./config/ --spring.profiles.active=${ACTIVE_PROFILE:-}"]