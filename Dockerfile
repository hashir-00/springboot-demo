#FROM openjdk:23-jdk-slim AS build
#
#LABEL authors="Hashirhalaldeen"
#WORKDIR /app
#
#RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
#
#COPY pom.xml .
#RUN mvn clean install -DskipTests
#COPY src ./src
#RUN mvn clean package -DskipTest
#
#
#COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
#
#ADD target/demo-0.0.1-SNAPSHOT.jar app1.jar
#EXPOSE 8080
#
#ENTRYPOINT [ "java", "-jar","app1.jar" ]

# Build Stage
#FROM openjdk:23-jdk-slim AS build
FROM maven:3.9.9-eclipse-temurin-23-alpine AS maven
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests
# Runtime Stage
FROM openjdk:23-jdk-slim
WORKDIR /app


COPY --from=maven app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

EXPOSE 8005

ENTRYPOINT ["java", "-jar", "/app/demo.jar"]

