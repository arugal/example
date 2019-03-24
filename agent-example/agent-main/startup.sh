#!/bin/sh

simple(){
    AGENT_PATH=/Users/zhangwei/Documents/Code/github/example/agent-example/agent-simple

    exec "java" -javaagent:${AGENT_PATH}/target/agent-simple-1.0.jar=hello -classpath target/agent-main-1.0.jar \
    com.github.arugal.example.agent.SimpleMainUp
}

bytebuddr(){
    AGENT_PATH=/Users/zhangwei/Documents/Code/github/example/agent-example/bytebuddy-agent/target
    exec "java" -javaagent:${AGENT_PATH}/bytebuddy-agent-example-1.0.jar -classpath target/agent-main-1.0.jar \
    com.github.arugal.example.agent.ByteBuddyMainUp
}


case $1 in
simple)
    simple
    ;;
bytebuddr)
    bytebuddr
    ;;
*)
    simple
;;
esac