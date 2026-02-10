FROM eclipse-temurin:25-jdk-alpine AS build

RUN apk add --no-cache maven

WORKDIR /pharmaflow

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /pharmaflow

COPY --from=build /pharmaflow/target/*.jar pharmaflow.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "pharmaflow.jar"]