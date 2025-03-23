FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY src /app/src
RUN gradle build -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/Pet-location-0.0.1-SNAPSHOT.jar /app/Pet-location.jar
EXPOSE 8080
CMD ["java", "-jar", "Pet-location.jar"]