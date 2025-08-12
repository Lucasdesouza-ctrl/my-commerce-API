FROM gradle:8.14.3-jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /ecommerce
COPY --from=build /home/gradle/src/build/libs/*.jar /ecommerce/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/ecommerce/app.jar"]