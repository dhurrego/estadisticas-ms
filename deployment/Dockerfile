FROM gradle:jdk17-alpine as builder

WORKDIR /app

COPY . /app

RUN gradle build

FROM amazoncorretto:17-alpine

ARG PORT_APP=8081
ENV PORT $PORT_APP

EXPOSE $PORT

COPY --from=builder /app/applications/app-service/build/libs/estadisticas-ms.jar /app/estadisticas-ms.jar

ENTRYPOINT [ "sh", "-c", "java -jar /app/estadisticas-ms.jar" ]
