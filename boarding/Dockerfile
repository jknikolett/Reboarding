FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./target/boarding-0.0.1-SNAPSHOT.jar /app/boarding-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/boarding-0.0.1-SNAPSHOT.jar"]