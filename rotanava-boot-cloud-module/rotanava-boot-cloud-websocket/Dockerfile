FROM frolvlad/alpine-java:jdk8-slim






VOLUME /tmp
ADD ./target/rotanava-boot-cloud-websocket-1.0.0.jar app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/app.jar"]
