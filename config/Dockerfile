FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./target/config-0.0.1-SNAPSHOT.jar /app/config-0.0.1-SNAPSHOT.jar
EXPOSE 9090
ADD ./central-repo-docker /app/central-repo-docker
ENTRYPOINT ["java","-jar","/app/config-0.0.1-SNAPSHOT.jar"]