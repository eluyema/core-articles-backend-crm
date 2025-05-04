# Use a Maven image with JDK 23 (if officially available) or build manually
FROM eclipse-temurin:23-jdk as builder
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . /app/
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

# Runtime image
FROM eclipse-temurin:23-jre as runtime
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
