FROM eclipse-temurin:17-jdk-focal AS runtime
WORKDIR /app
# RUN mvn clean install -DskipTests after every change and rebuild the image
COPY ./target/redis-autocomplete-0.0.1-SNAPSHOT.jar /app/redis-autocomplete.jar
CMD ["java", "-jar", "redis-autocomplete.jar"]
