FROM openjdk:17
COPY target/library-*.jar /library-app.jar
ENTRYPOINT ["java", "-jar", "/library-app.jar"]