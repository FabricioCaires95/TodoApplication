FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/application-todo-1.0-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} todo-backend.jar

CMD ["java", "-jar", "todo-backend.jar"]