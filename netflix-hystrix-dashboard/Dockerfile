# Dockerfile: netflix-hystrix-dashboard

FROM java:8

RUN mkdir /workdir

ADD target/netflix-hystrix-dashboard-0.0.1-SNAPSHOT.jar /workdir/netflix-hystrix-dashboard.jar

EXPOSE 7979

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/netflix-hystrix-dashboard.jar"]
