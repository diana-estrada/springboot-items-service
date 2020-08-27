FROM openjdk:8
VOLUME /tmp
EXPOSE 9191
ADD springboot-service-item-0.0.1-SNAPSHOT.jar service-items.jar
ENTRYPOINT ["java", "-jar", "/service-items.jar"]