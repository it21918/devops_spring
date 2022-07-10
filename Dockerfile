FROM openjdk:8
COPY ./target/spring-boot-security-jwt.jar devops
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "devops"]
