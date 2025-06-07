FROM gradle:8.7-jdk21 AS builder
WORKDIR /home/gradle/project

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle wrapper --gradle-version 8.7 --distribution-type all --no-daemon \
    && gradle dependencies --no-daemon

#Copy source và chạy Spotless để format code trước build
COPY src ./src
RUN ./gradlew spotlessApply --no-daemon


#Buid bỏ test
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
