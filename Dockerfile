#FROM openjdk:8u181-jdk-stretch as builder
#COPY . /src
#RUN cd /src && chmod +xrw ./gradlew && ./gradlew assemble

FROM openjdk:8u181-jre-slim
COPY /build/libs/pc-security.jar pc-security.jar
ENTRYPOINT ["java",  "-jar","/pc-security.jar"]
