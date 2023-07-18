FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./KurdDevs/target/docker-spring-boot.jar docker-spring-boot.jar
ENTRYPOINT ["java","-jar","/docker-spring-boot.jar"]
EXPOSE 8080