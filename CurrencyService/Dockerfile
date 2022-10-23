FROM openjdk:11
ARG JAR_FILE=WareHouseService-*.jar
COPY target/${JAR_FILE} WareHouseService.jar
ENTRYPOINT ["java", "-jar", "WareHouseService.jar"]