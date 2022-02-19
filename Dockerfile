#FROM maven:3.8.2-jdk-11
#WORKDIR /drone
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run
#EXPOSE 9094

FROM openjdk:11
ADD target/*.jar drone_application
EXPOSE 9094
ENTRYPOINT ["java","-jar","drone_application"]
