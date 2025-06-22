FROM gradle:8.4-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY scrapper ./scrapper
COPY link-parser ./link-parser
COPY bot ./bot

RUN gradle :bot:dependencies --no-daemon

COPY . .

RUN gradle :bot:build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/bot/build/libs/bot-*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]