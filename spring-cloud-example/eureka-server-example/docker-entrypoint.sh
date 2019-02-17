#!/bin/sh


JAVA_OPTS="-Xmx128M -Xms64M"

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

echo $JAVA_OPTS

java -jar $JAVA_OPTS $JAR_NAME