FROM eclipse-temurin:17.0.13_6-jdk-alpine AS BUILDER
WORKDIR /opt/app

COPY scrapper ./scrapper
COPY link-parser ./link-parser
COPY bot ./bot

COPY settings.gradle.kts build.gradle.kts ./
COPY gradle.properties ./gradle.properties
COPY gradle ./gradle
COPY .gitignore ./

RUN chmod +x ./gradlew && \
    ./gradlew build --no-daemon --no-build-cache --parallel -p scrapper

FROM eclipse-temurin:17.0.13_6-jre-alpine

WORKDIR /opt/app

COPY --from=builder /opt/app/scrapper/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]