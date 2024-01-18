FROM amazoncorretto:17-alpine-jdk
LABEL authors="aleksandr"

ENTRYPOINT ["top", "-b"]