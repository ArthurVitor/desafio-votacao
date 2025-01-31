# Use the official Ubuntu base image
FROM ubuntu:22.04

# Set environment variables for Java
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Install necessary dependencies
RUN apt-get update && \
    apt-get install -y \
    openjdk-21-jdk \
    maven \
    wget \
    curl \
    && apt-get clean

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project to the container
COPY . .

# Build the Java application using Maven
RUN mvn clean install -DskipTests

# Expose the port the app will run on
EXPOSE 8080

# Command to run the application (Spring Boot jar file)
CMD ["java", "-jar", "target/votacao-api-0.0.1-SNAPSHOT.jar"]
