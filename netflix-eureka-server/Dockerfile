# Dockerfile: netflix-eureka-server

FROM java:8

RUN mkdir /workdir

ADD target/netflix-eureka-server-0.0.1-SNAPSHOT.jar /workdir/netflix-eureka-server.jar

EXPOSE 8761

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/netflix-eureka-server.jar"]
