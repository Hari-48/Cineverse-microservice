# Use a lightweight JDK base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/tamil-movies.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8888

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]