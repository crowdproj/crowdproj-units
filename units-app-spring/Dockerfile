FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/units-app-spring-0.0.1.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]
