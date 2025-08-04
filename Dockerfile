######## Build Image ########

FROM alpine:3.22.0@sha256:8a1f59ffb675680d47db6337b49d22281a139e9d709335b492be023728e11715 AS build

RUN apk add curl --no-cache

RUN curl --fail-with-body -L -o opentelemetry-javaagent.jar 'https://repo1.maven.org/maven2/io/opentelemetry/javaagent/opentelemetry-javaagent/2.17.0/opentelemetry-javaagent-2.17.0.jar'

######## Service Image ########

FROM eclipse-temurin:21.0.7_6-jre-alpine@sha256:8728e354e012e18310faa7f364d00185277dec741f4f6d593af6c61fc0eb15fd

RUN apk add shadow --no-cache && \
    useradd --create-home --home /home/pleo --shell /bin/false pleo

EXPOSE 8080 8081

USER pleo

WORKDIR /home/pleo

COPY --from=build opentelemetry-javaagent.jar opentelemetry-javaagent.jar

COPY pleo-and-another-nicholas-testing-directory-changes-nicholas-app/build/libs/pleo-and-another-nicholas-testing-directory-changes-nicholas-app.jar pleo-and-another-nicholas-testing-directory-changes-nicholas-app.jar


ENTRYPOINT [ \
    "java", \
    "-javaagent:/home/pleo/opentelemetry-javaagent.jar", \
    "-Dapplication.config.additionalUrls=file:///config/application.properties", \
    "-XX:MaxRAMPercentage=80", \
    # To avoid the "Sharing is only supported for boot loader classes because bootstrap classpath has been appended" harmless but noisy error,
    # see: https://stackoverflow.com/a/57957031
    "-Xshare:off", \
    "-jar", \
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-app.jar" \
    ]
