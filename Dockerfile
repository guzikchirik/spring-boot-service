FROM openjdk:17

RUN mkdir -p /usr/src/app/
WORKDIR /usr/src/app/

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /usr/src/app/

CMD ["java","-jar", "demo-0.0.1-SNAPSHOT.jar"]