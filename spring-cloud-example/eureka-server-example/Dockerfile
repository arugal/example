FROM hengyunabc/arthas

RUN mkdir /home/eureka-server

COPY target/eureka-server-example-1.0.jar /home/eureka-server/eureka-server-example-1.0.jar

COPY target/lib /home/eureka-server/lib

COPY docker-entrypoint.sh /home/eureka-server/docker-entrypoint.sh

RUN chmod +x /home/eureka-server/docker-entrypoint.sh

WORKDIR /home/eureka-server

EXPOSE 9001

ENTRYPOINT ["./docker-entrypoint.sh", "eureka-server-example-1.0.jar"]