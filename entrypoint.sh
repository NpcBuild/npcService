#!/bin/bash

java $JAVA_OPTS -jar /start-1.0.0.jar
#&
## 其他逻辑，使得Java 程序是容器内的子进程，在这种情况下可以实现停止java进程后更新jar包再次启动，而无需重新构建镜像
#while true; do
#    sleep 1
#done