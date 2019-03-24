FROM hengyunabc/arthas

RUN mkdir /home/eureka-client

COPY eureka-client-example-1.0.jar /home/eureka-client/eureka-client-example-1.0.jar

COPY lib /home/eureka-client/lib

COPY docker-entrypoint.sh /home/eureka-client/docker-entrypoint.sh

RUN chmod +x /home/eureka-client/docker-entrypoint.sh

WORKDIR /home/eureka-client

ENTRYPOINT ["./docker-entrypoint.sh", "eureka-client-example-1.0.jar"]