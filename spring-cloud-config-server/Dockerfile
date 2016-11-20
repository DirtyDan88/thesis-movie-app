# Dockerfile: spring-cloud-config-server

FROM java:8

RUN mkdir /workdir

ADD target/spring-cloud-config-server-0.0.1-SNAPSHOT.jar /workdir/spring-cloud-config-server.jar

EXPOSE 8888

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/spring-cloud-config-server.jar"]
