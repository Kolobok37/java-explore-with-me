FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=target/stats-server-0.0.1-SNAPSHOT.jar
COPY target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9091
WORKDIR /java
ENTRYPOINT ["java","-jar","/app.jar"]