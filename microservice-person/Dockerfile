# Dockerfile: microservice-person

FROM java:8

RUN mkdir /workdir

ADD target/microservice-person-0.0.1-SNAPSHOT.jar /workdir/microservice-person.jar

EXPOSE 8070

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "/workdir/microservice-person.jar"]
