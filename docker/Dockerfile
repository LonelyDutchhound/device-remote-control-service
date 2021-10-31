FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
ENV TZ=Europe/Moscow
LABEL authors="lonely.dutchhound@gmail.com>"
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]


