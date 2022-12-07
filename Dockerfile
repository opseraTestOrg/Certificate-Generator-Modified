FROM gradle:6.9.0-jdk11 AS build
ENV DOCKER_ENV=dev
ARG jfrog_password_arg
ENV jfrog_password=$jfrog_password_arg
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM eclipse-temurin:11-jre
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /apps/OpsERA/components/certificate-generator/certificate-generator.jar
EXPOSE 8055
ENTRYPOINT exec /tini -- java -XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0 -Dspring.profiles.active=$DOCKER_ENV -Djava.security.egd=file:/dev/./urandom -jar /apps/OpsERA/components/certificate-generator/certificate-generator.jar