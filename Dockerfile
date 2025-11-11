FROM openjdk:26-ea-17-slim
VOLUME /tpm
COPY TwitchUrlExtractionTool.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
