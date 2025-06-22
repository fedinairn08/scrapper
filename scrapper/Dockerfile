FROM gradle:8.4-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY scrapper ./scrapper
COPY link-parser ./link-parser
COPY bot ./bot

RUN gradle :scrapper:dependencies --no-daemon

COPY . .

RUN gradle :scrapper:build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/scrapper/build/libs/scrapper-*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]