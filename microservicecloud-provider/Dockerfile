FROM java:8
VOLUME /data/logs:/data/logs
VOLUME /tmp
COPY ./target/*.jar app.jar
RUN touch app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
