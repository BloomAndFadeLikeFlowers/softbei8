FROM java:8
VOLUME /data/logs:/data/logs
VOLUME /tmp
ADD ./target/*.jar app.jar
RUN touch app.jar
ENTRYPOINT ["java","-jar","app.jar"]
