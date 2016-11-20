# Dockerfile: netflix-turbine-aggregator

FROM java:8

RUN mkdir /workdir

ADD target/netflix-turbine-aggregator-0.0.1-SNAPSHOT.jar /workdir/netflix-turbine-aggregator.jar

EXPOSE 8989

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/netflix-turbine-aggregator.jar"]
