#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080

# Use an official OpenJDK runtime as the base image
FROM adoptopenjdk/openjdk17:alpine-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory to the containe
COPY target/*.jar app.jar

# Specify the command to run when the container starts
CMD ["java", "-jar", "app.jar"]
