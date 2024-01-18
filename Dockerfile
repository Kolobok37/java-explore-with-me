FROM amazoncorretto:11-alpine-jdk
LABEL authors="aleksandr"

ENTRYPOINT ["top", "-b"]