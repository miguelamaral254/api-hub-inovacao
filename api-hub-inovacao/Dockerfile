# Etapa 1: Construção da aplicação
FROM maven:3.9.0-openjdk-23 AS builder
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]