# Use the official OpenJDK 17 slim image as the base image
FROM openjdk:17-jdk-slim

# Copy the JAR file from the build context to the image
COPY target/clusteredDataWarehouse.jar /app.jar

# Expose port 5000
EXPOSE 5000

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]

