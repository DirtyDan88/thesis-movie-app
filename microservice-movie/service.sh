#!/bin/sh

## start multiple instances of the service ##

LOCATION=$(dirname "$(readlink -e "$0")")

case "$1" in
  start)
    for i in $(seq 1 $2); do
      port=`expr $i + 8090`
      logFile=$LOCATION'/instance-'$port'.log'
      echo 'starting instance on port '$port
      java -jar target/microservice-movie-0.0.1-SNAPSHOT.jar --server.port=$port > $logFile 2>&1 &
      pid=$!
      echo $pid > $LOCATION'/.port-'$port'.pid'
    done
    ;;
  stop)
    for file in $(find -name \*.pid); do
      pidFile=$LOCATION'/'$file
      pid=$(cat $pidFile)
      echo 'stopping instance with pid '$pid
      kill -15 $pid
      rm $pidFile
    done
    ;;
  *)
    echo "Usage: $0 {start <num-instances> | stop}"
    exit 1
    ;;
esac
