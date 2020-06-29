FROM openjdk:8-jre

WORKDIR /app
COPY ./target/visualization-0.0.1-SNAPSHOT.jar /app/visualization-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/visualization-0.0.1-SNAPSHOT.jar"]