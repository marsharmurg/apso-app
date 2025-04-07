FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=0 /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
