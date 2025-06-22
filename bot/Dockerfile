FROM gradle:8.4-jdk17-alpine AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY bot/build.gradle ./bot/
COPY link-parser/build.gradle ./link-parser/

RUN gradle downloadDependencies

COPY . .

RUN gradle :bot:build --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/bot/build/libs/bot-*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]