FROM openjdk:23-jdk-slim

LABEL authors="Hashirhalaldeen"
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

ADD target/demo-0.0.1-SNAPSHOT.jar app1.jar

ENTRYPOINT [ "java", "-jar","app1.jar" ]
EXPOSE 8080