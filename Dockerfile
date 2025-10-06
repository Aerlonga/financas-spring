# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-focal

# Arguments for user and group IDs to avoid permission issues
ARG UID=1000
ARG GID=1000

# Create a non-root user
RUN groupadd -g $GID -o appgroup && \
    useradd -m -u $UID -g $GID -o -s /bin/bash appuser

# Set working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/FinancasSpring-*.jar app.jar

# Create a logs directory and change ownership
RUN mkdir logs && chown appuser:appgroup logs

# Switch to the non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

