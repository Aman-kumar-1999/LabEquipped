# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/service-registry.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

# To build the Docker image, run:
#   command : docker build -t service-registry .
# To run the Docker container, use:
#   command : docker run -d -p 8761:8761 --name service-registry service-registry

