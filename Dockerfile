FROM ubuntu:latest

LABEL authors="Hashirhalaldeen"

ADD target/demo-0.0.1-SNAPSHOT.jar app1.jar

ENTRYPOINT [ "java", "-jar","app1.jar" ]