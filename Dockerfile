FROM amazoncorretto:21 as builder
WORKDIR /opt/app
RUN yum install -y tar gzip

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY .env /opt/app/.env
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM amazoncorretto:21
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]