#FROM hub.c.163.com/library/java:latest
FROM hub.c.163.com/library/java
VOLUME /tmp
ADD start/target/start-1.0.0.jar /start-1.0.0.jar
# -Ddruid.mysql.usePingMethod=false
ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai -Ddruid.mysql.usePingMethod=false"
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]