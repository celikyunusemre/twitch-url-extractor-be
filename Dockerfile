FROM openjdk:17
VOLUME /tpm
COPY TwitchUrlExtractionTool.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080