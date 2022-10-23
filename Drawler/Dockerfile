FROM openjdk:11
ARG JAR_FILE=Drawler-*.jar
COPY target/${JAR_FILE} Drawler.jar
ENTRYPOINT ["java", "-jar", "Drawler.jar"]