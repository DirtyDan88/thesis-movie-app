# Dockerfile: api-gateway

FROM java:8

RUN mkdir /workdir

ADD target/api-gateway-0.0.1-SNAPSHOT.jar /workdir/api-gateway.jar

EXPOSE 8080

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/api-gateway.jar"]
