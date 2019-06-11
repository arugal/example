package com.github.arugal.example.grpc;

import com.github.arugal.example.grpc.gencode.HelloRequest;
import com.github.arugal.example.grpc.gencode.HelloResponse;
import com.github.arugal.example.grpc.gencode.HelloSericeGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author: zhangwei
 * @date: 23:53/2019-03-26
 */
public class HelloServiceImpl extends HelloSericeGrpc.HelloSericeImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = new StringBuilder("Hello,")
                .append(request.getFirstName())
                .append("-")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();;
    }


    @Override
    public StreamObserver<HelloRequest> collect(StreamObserver<HelloResponse> responseObserver) {
        return super.collect(responseObserver);
    }
}
