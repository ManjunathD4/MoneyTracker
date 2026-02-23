#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY target/expensetracker-0.0.1-SNAPSHOT.jar expensetracker-v1.0.jar
#EXPOSE 9090
#ENTRYPOINT ["java","-jar","expensetracker-v1.0.jar"]

# ---- Build Stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/expensetracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]