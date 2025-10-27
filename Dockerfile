# Bygg fasen
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY MyJournalApi/ .
RUN mvn clean package -DskipTests

# Kör fasen
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
