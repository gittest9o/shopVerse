FROM openjdk:24-ea-jdk-slim
WORKDIR /app

# Копируем JAR и конфигурационные файлы
COPY target/*.jar app.jar
COPY src/main/resources/application.yml ./config/

# Открываем порт сервиса
EXPOSE 8099

# Запускаем приложение
CMD ["java", "-jar", "app.jar", "--spring.config.location=classpath:/,file:./config/"]