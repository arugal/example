#!/bin/sh


JAVA_OPTS="-Xmx64M -Xms32M"

remote_debug(){
    ADDRESS=
    if [[ ! -z "$1" && "$1" != *[!0-9]* ]]; then
        ADDRESS="$1"
    else
        ADDRESS="5005"
    fi
    JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$ADDRESS"
}


JAR_NAME="$1"
shift
if [[ "$1" = 'remote_debug' ]]; then
    shift
    remote_debug $*
fi

if [[ -z "$server_port" ]]; then
    server_port=$((9091+RANDOM%50+1))
fi

if [[ ! -z "$server_port" ]]; then
    JAVA_OPTS="$JAVA_OPTS -Dserver.port=$server_port"
fi

if [[ -z "$application_name" ]]; then
    application_name="eureka-producer"
fi

if [[ ! -z "$application_name" ]]; then
    JAVA_OPTS="$JAVA_OPTS -Dspring.application.name=$application_name"
fi

echo $JAVA_OPTS

java -jar $JAVA_OPTS $JAR_NAME