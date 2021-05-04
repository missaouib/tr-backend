#Stage 1: Build the application
FROM gradle:6.5.1-jdk8 AS build

#Set the current working directory inside the IMAGE
WORKDIR /app

#Copy gradle executable to the IMAGE
COPY --chown=gradle:gradle . /app

# Build all the dependencies
RUN gradle build --no-daemon -x test



#Stage 2: A minimal docker image with command to run the app
FROM openjdk:8-jdk-alpine

USER root
RUN apk --update add fontconfig ttf-dejavu

MAINTAINER Stefan Roganovic <stefan.roganovic@gmail.com>

COPY --from=build /app/build/libs/app-0.0.1-SNAPSHOT.jar backend-app.jar 
ENTRYPOINT ["java", "-jar", "/backend-app.jar"]
EXPOSE 8080
