# Scrapper

## [Ссылка на телеграмм бота](https://t.me/scrapper_tg_bot)

## Стек технологий

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)

Многомодульное `Gradle` `Java` приложение с использованием `Spring Boot`.
Приложение позволяет парсить различные ресурсы, например `GitHub` или `StackOverflow`, и присылать уведомление
об обновлении в телеграмм бота, через которого происходит управление отслеживаемыми ссылками.

Общение сервисов происходит через `REST`.
Отправление обновлений отслеживаемых ресурсов происходит через `RabbitMQ`,
для которого также реализован паттерн `Dead Letter Queue`.

Существуют реализации доступа к данным через `JDBC` и `SpringData + Hibernate`.
Для создания базы данных используется механизм миграций `Liquibase`.
Обе реализации покрыты модульными и интеграционными тестами с использованием `тест-контейнеров`.

Обработка ссылок происходит в отдельном модуле, где обработчики собраны по паттерну `Цепочка-обязанностей`.
С использованием этого паттерна также созданы обработчики команд в сервисе с ботом.
Реализован паттерн с использованием `Reflection`.

Для модулей bot и scrapper API реализовано по спецификации `OpenAPI`. Для обоих проектов настроен Swagger.

Для модулей написаны `Dockerfile`.
Все модули компилируются независимо от платформы в Docker контейнерах.
Для сборки всего проекта создан `docker-compose` файл.

Для мониторинга настроены `Grafana` и `Prometheus`. В Grafana создана доска Scrapper.

Для проекта создан `Checkstyle`. Также настроен `CI/CD` на основе сервиса `GitHub Actions`,
где собирается проект, создается `Docker Image` проекта и выгружается в `GitHub Packages`,
также там проверяется Checkstyle.

## Quick Start

Для запуска потребуется указать переменные окружения:
- `BOT_TOKEN` - токен телеграмм бота (Можно получить у [BotFather](https://t.me/BotFather))
- `GITHUB_TOKEN` - [токен GitHub аккаунта](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

Запуск через докер:
```shell
docker compose up -d 
```

## Примеры отслеживаемых ссылок

- `Вопрос на StackOverflow` - [Пример](https://stackoverflow.com/questions/927358/how-do-i-undo-the-most-recent-local-commits-in-git)
- `Репозиторий GitHub` - [Пример](https://github.com/fedinairn08/scrapper)

## Endpoints

- `localhost:8080` - Сервис с телеграмм ботом
- `localhost:8090/metrics` - Prometheus metrics сервиса с телеграмм ботом
- `localhost:8080/swagger-ui.html` - Swagger сервиса с телеграмм ботом
- `localhost:8081` - Парсер-сервис
- `localhost:8081/swagger-ui.html` - Swagger парсер-сервиса
- `localhost:8091/metrics` - Prometheus metrics парсер-сервиса
- `localhost:3000` - Grafana с добавленной доской Scrapper

