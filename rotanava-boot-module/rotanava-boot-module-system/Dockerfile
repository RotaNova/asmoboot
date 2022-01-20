FROM  registry.cn-hangzhou.aliyuncs.com/rotanova/rotanavajdk:v6-font

ENV minioAccessKey minioAccessKey

ENV minioEndpoint minioEndpoint

ENV minioSecretKey minioSecretKey

ENV datasourcePwd datasourcePwd

ENV rabbitmqPwd rabbitmqPwd

ENV rabbitmqUser rabbitmqUser



VOLUME /tmp
ADD ./target/rotanava-boot-module-system-1.0.0.jar app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/app.jar","--minio.accessKey=${minioAccessKey}","--minio.endpoint=${minioEndpoint}","--minio.secretKey=${minioSecretKey}","--spring.datasource.password=${datasourcePwd}","--spring.rabbitmq.password=${rabbitmqPwd}","--spring.rabbitmq.username=${rabbitmqUser}"]
