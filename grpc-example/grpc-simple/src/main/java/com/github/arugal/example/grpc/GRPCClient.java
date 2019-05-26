package com.github.arugal.example.grpc;

import com.github.arugal.example.grpc.gencode.HelloRequest;
import com.github.arugal.example.grpc.gencode.HelloResponse;
import com.github.arugal.example.grpc.gencode.HelloSericeGrpc;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 23:59/2019-03-26
 */
public class GRPCClient {


    public static void main(String[] args) {
        ManagedChannel channel = null;
        try {
            channel =
                    ManagedChannelBuilder.forAddress(args[0], Integer.valueOf(args[1])).usePlaintext(true).build();
            HelloSericeGrpc.HelloSericeBlockingStub stub = HelloSericeGrpc.newBlockingStub(channel);
            HelloResponse response = stub.hello(HelloRequest.newBuilder()
                    .setFirstName("zhang")
                    .setLastName("wei")
                    .build());
            System.out.println(response.getGreeting());

            HelloSericeGrpc.HelloSericeFutureStub futureStub = HelloSericeGrpc.newFutureStub(channel);
            ListenableFuture future = futureStub.hello(HelloRequest.newBuilder()
                    .setFirstName("li")
                    .setLastName("li")
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }

}
