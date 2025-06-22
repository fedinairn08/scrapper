FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /opt/app

COPY gradle ./gradle
COPY gradlew .
COPY gradle.properties .
COPY settings.gradle.kts .
COPY build.gradle.kts .

RUN ./gradlew dependencies

COPY scrapper ./scrapper
COPY link-parser ./link-parser
COPY bot ./bot

RUN chmod +x ./gradlew && \
    ./gradlew build --no-daemon --no-build-cache --parallel -p scrapper

FROM eclipse-temurin:17-jre-alpine

WORKDIR /opt/app

COPY --from=builder /opt/app/scrapper/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]