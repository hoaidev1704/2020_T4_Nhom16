FROM openjdk:11
ARG JAR_FILE=Drawler-*.jar
COPY target/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]