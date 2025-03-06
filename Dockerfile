FROM openjdk:17-jdk-slim
VOLUME tmp
MAINTAINER 4th Platform
VOLUME /tmp
ARG JAR_FILE
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar