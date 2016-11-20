# Dockerfile: microservice-movie

FROM java:8

RUN mkdir /workdir

ADD target/microservice-movie-0.0.1-SNAPSHOT.jar /workdir/microservice-movie.jar

EXPOSE 8090

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/microservice-movie.jar"]
