FROM openjdk:8
EXPOSE 8080
ADD target/docker-project.jar docker-project.jar
ENTRYPOINT ["java", "-jar","/docker-project.jar" ]