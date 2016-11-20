#!/bin/sh

mvn clean package

# terminate all exited containers
docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs sudo docker rm

appname='microservice-movie'

# build the container
docker build -t $appname .
