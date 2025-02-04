FROM ubuntu:22.04 AS builder

# Instalar dependências e JDK
RUN apt-get update && \
    apt-get install -y software-properties-common \
    && add-apt-repository ppa:openjdk-r/ppa \
    && apt-get update && \
    apt-get install -y openjdk-23-jdk maven

WORKDIR /app

COPY . /app

RUN mvn clean install

FROM openjdk:23

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]