FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/GymRegistration-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} gym_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gym_app.jar"]