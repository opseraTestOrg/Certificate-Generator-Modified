FROM gradle:6.3.0-jdk8 AS build
ENV DOCKER_ENV=dev
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:8-jre-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /apps/OpsERA/components/certificate-generator/certificate-generator.jar
EXPOSE 8055
ENTRYPOINT java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dspring.profiles.active=$DOCKER_ENV $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /apps/OpsERA/components/certificate-generator/certificate-generator.jar