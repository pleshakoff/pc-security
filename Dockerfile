FROM openjdk:8u181-jre-slim
COPY /build/libs/pc-security.jar pc-security.jar
ENTRYPOINT ["java",  "-jar","/pc-security.jar"]
