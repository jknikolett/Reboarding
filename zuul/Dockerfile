FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./target/zuul-0.0.1-SNAPSHOT.jar /app/zuul-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/zuul-0.0.1-SNAPSHOT.jar"]