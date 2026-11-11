FROM maven:3.9.11-eclipse-temurin-25 AS build
ARG MODULE
WORKDIR /workspace
COPY . .
RUN mvn -B -pl ${MODULE} -am package -DskipTests && cp ${MODULE}/target/${MODULE}.jar /app.jar

FROM eclipse-temurin:25-jre
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /app.jar app.jar
USER 10001
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70", "-jar", "/app/app.jar"]
