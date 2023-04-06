FROM openjdk:11-jdk

WORKDIR /home/spring

COPY build/libs/simpleTripBE-0.0.1-SNAPSHOT.jar /home/spring/app.jar

CMD ["java","-Dspring.profiles.active=prod", "-jar", "/home/spring/app.jar"]