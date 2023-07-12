# Use a base image with Java 17 installed
FROM openjdk:17-jdk


# Set the working directory inside the container
WORKDIR /app

# Copy the necessary files to the container
COPY pom.xml .
COPY src ./src

# Build the Maven project
RUN mvn clean package

# Expose the port on which your application will run
EXPOSE 8080

# Set the command to run your application
CMD ["java", "-jar", "target/KurdDevs-0.0.1-SNAPSHOT.jar"]


##
## Build stage
##
#FROM maven:3.8.2-jdk-11 AS build
#COPY . .
#RUN mvn clean package -Pprod -DskipTests
#
##
## Package stage
##
#FROM openjdk:11-jdk-slim
#
## ENV PORT=8080
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","demo.jar"]