FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./target/eureka-0.0.1-SNAPSHOT.jar /app/eureka-0.0.1-SNAPSHOT.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar","/app/eureka-0.0.1-SNAPSHOT.jar"]