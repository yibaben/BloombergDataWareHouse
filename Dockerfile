FROM openjdk:17-jdk-slim
ADD target/clusteredDataWarehouse.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
