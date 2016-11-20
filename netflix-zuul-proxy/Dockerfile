# Dockerfile: netflix-zuul-proxy

FROM java:8

RUN mkdir /workdir

ADD target/netflix-zuul-proxy-0.0.1-SNAPSHOT.jar /workdir/netflix-zuul-proxy.jar

EXPOSE 8765

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/netflix-zuul-proxy.jar"]
