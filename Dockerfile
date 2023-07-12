FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ../KurdDevs/KurdDevs/target/docker-spring-boot.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080