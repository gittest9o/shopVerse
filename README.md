
## Описание проекта

ShopVerse — это учебный проект интернет-магазина с микросервисной архитектурой, написанный на Spring Boot. Проект демонстрирует:
  
- взаимодействие через REST  
- конфигурационный сервер, сервис обнаружения (Eureka)  
- API Gateway  
- мониторинг / метрики

Проект не претендует на коммерческое качество. Front сделан с помощью ИИ

---

## Функционал

| Сервис                  | Основные возможности |
|-------------------------|-----------------------|
| Admin                   | Добавление товаров |
| Auth / User             | Регистрация, логин, JWT-аутентификация |
| Cart                    | Добавление/удаление из корзины, отображение корзины |
| Elasticsearch           | Поиск по товарам |
| Product                 | CRUD-операции, главная страница |
| Order                   | Создание заказа |
| Notification            | Отправка уведомлений пользователю (email) |
| Gateway                 | Проксирование запросов, маршруты, фильтры |
| Мониторинг              | Сбор метрик (Prometheus / Actuator) |


---
## Технологии

- Java 24
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- OpenFeign client
- REST API
- JUnit/Mockito
- JPA
- MySQL
- Elasticsearch
- Redis
- Kafka  
- JWT
- Docker + Docker Compose  
- Prometheus / Actuator / Micrometer (метрики)
- Grafana
- Swagger
---

##  Запуск проекта локально

### Требования
- Java 17+
- Maven 3+
- Docker и Docker Compose

### Шаги

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/gittest9o/shopVerse.git
   cd shopVerse
   git checkout docker
2. Собрать проект:
    ```bash
       mvn clean package -DskipTests
3.Запустить через Docker Compose:
    ```
       docker-compose up --build```

4. Открыть:
Gateway → http://localhost:8080
