clean:
	mvn clean
install: clean
	mvn install
deploy: install
	scp target/eureka-client-example-1.0.jar root@dell.com:/root/eureka/client/ && \
	scp -r target/lib/ root@dell.com:/root/eureka/client/lib && \
	scp Dockerfile root@dell.com:/root/eureka/client/ && \
	scp docker*.sh root@dell.com:/root/eureka/client