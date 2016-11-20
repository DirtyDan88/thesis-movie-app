# Dockerfile: spring-cloud-admin-server

FROM java:8

RUN mkdir /workdir

ADD target/spring-cloud-admin-server-0.0.1-SNAPSHOT.jar /workdir/spring-cloud-admin-server.jar

EXPOSE 8766

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/spring-cloud-admin-server.jar"]
