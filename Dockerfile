#FROM hub.c.163.com/library/java:latest
FROM hub.c.163.com/library/java
VOLUME /tmp
ADD start/target/start-1.0.0.jar /start-1.0.0.jar
ENTRYPOINT ["java","-jar","/start-1.0.0.jar"]