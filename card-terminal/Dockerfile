FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./target/card-terminal-0.0.1-SNAPSHOT.jar /app/card-terminal-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/card-terminal-0.0.1-SNAPSHOT.jar"]