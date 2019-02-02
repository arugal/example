#!/bin/sh

# 修改配置
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos-spring-boot&group=nacos-example&content=useLocalCache=true"

# 注册服务
curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos-example&ip=127.0.0.1&port=8080'

