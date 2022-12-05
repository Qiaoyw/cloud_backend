FROM maven:3.5.2-jdk-8-alpine as build-stage
COPY settings.xml /usr/share/maven/conf/settings.xml
COPY pom.xml /tmp/warehouse/pom.xml
COPY . .
RUN mvn clean package

# production stage
FROM openjdk:8 as production-stage
COPY --from=build-stage ./target/covid_19_backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "-Duser.timezone=GMT+08", "app.jar"]
