FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/ewm-service-0.0.1-SNAPSHOT.jar
COPY target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
WORKDIR /java
ENTRYPOINT ["java","-jar","/app.jar"]