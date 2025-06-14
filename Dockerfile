#FROM hub.c.163.com/library/java:latest
#FROM hub.c.163.com/library/java
FROM openjdk:8-jdk
MAINTAINER NPC <npctovc@gmail.com>
VOLUME /tmp
ADD start/target/start-1.0.0.jar /start-1.0.0.jar
ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai -Ddruid.mysql.usePingMethod=false"
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]