package com.github.arugal.example.grpc;

import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author: zhangwei
 * @date: 23:50/2019-03-26
 */
public class GRPCServer {

    private io.grpc.Server server;

    private int port;

    public GRPCServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            server = ServerBuilder.forPort(port)
                    .addService(new HelloServiceImpl())
                    .build()
                    .start();
            System.out.println("start gRpc server successful, listenting on "+port);
        }catch (Exception e){
            System.out.println("start gRPC server loser!");
            System.exit(0);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdownNow();
                System.out.println("hook stop gRPC server!");
            }
        }));
        try {
            server.awaitTermination();
        }catch (Exception e){
            // ignore
        }
    }


    public static void main(String[] args) {
        GRPCServer grpcServer = new GRPCServer(9999);
        grpcServer.start();
    }


}
