FROM adoptopenjdk:11-jre-hotspot
ADD KurdDevs/target/docker-spring-boot.jar docker-spring-boot.jar
ENTRYPOINT ["java", "-jar", "docker-spring-boot.jar"]


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