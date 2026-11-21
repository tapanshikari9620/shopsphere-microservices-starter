
# Use Java 17 runtime
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy Spring Boot executable JAR
COPY target/product-service-1.0.0.jar app.jar

# Expose application port
EXPOSE 8081

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
