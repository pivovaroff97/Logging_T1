FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} loggingOperationsApp.jar
ENTRYPOINT ["java", "-jar", "loggingOperationsApp.jar"]