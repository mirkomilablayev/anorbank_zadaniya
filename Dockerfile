FROM openjdk:8-jdk-alpine
ADD target/anorbank_zadaniya_log_etries_saver-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]