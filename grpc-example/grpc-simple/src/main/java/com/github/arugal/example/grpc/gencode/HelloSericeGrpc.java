package com.github.arugal.example.grpc.gencode;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.19.0)",
    comments = "Source: HelloSerive.proto")
public final class HelloSericeGrpc {

  private HelloSericeGrpc() {}

  public static final String SERVICE_NAME = "com.github.arugal.example.grpc.gencode.HelloSerice";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.arugal.example.grpc.gencode.HelloRequest,
      com.github.arugal.example.grpc.gencode.HelloResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = com.github.arugal.example.grpc.gencode.HelloRequest.class,
      responseType = com.github.arugal.example.grpc.gencode.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.arugal.example.grpc.gencode.HelloRequest,
      com.github.arugal.example.grpc.gencode.HelloResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<com.github.arugal.example.grpc.gencode.HelloRequest, com.github.arugal.example.grpc.gencode.HelloResponse> getHelloMethod;
    if ((getHelloMethod = HelloSericeGrpc.getHelloMethod) == null) {
      synchronized (HelloSericeGrpc.class) {
        if ((getHelloMethod = HelloSericeGrpc.getHelloMethod) == null) {
          HelloSericeGrpc.getHelloMethod = getHelloMethod = 
              io.grpc.MethodDescriptor.<com.github.arugal.example.grpc.gencode.HelloRequest, com.github.arugal.example.grpc.gencode.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.github.arugal.example.grpc.gencode.HelloSerice", "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.arugal.example.grpc.gencode.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.arugal.example.grpc.gencode.HelloResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HelloSericeMethodDescriptorSupplier("hello"))
                  .build();
          }
        }
     }
     return getHelloMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HelloSericeStub newStub(io.grpc.Channel channel) {
    return new HelloSericeStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HelloSericeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HelloSericeBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HelloSericeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HelloSericeFutureStub(channel);
  }

  /**
   */
  public static abstract class HelloSericeImplBase implements io.grpc.BindableService {

    /**
     */
    public void hello(com.github.arugal.example.grpc.gencode.HelloRequest request,
        io.grpc.stub.StreamObserver<com.github.arugal.example.grpc.gencode.HelloResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHelloMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.arugal.example.grpc.gencode.HelloRequest,
                com.github.arugal.example.grpc.gencode.HelloResponse>(
                  this, METHODID_HELLO)))
          .build();
    }
  }

  /**
   */
  public static final class HelloSericeStub extends io.grpc.stub.AbstractStub<HelloSericeStub> {
    private HelloSericeStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HelloSericeStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloSericeStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HelloSericeStub(channel, callOptions);
    }

    /**
     */
    public void hello(com.github.arugal.example.grpc.gencode.HelloRequest request,
        io.grpc.stub.StreamObserver<com.github.arugal.example.grpc.gencode.HelloResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HelloSericeBlockingStub extends io.grpc.stub.AbstractStub<HelloSericeBlockingStub> {
    private HelloSericeBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HelloSericeBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloSericeBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HelloSericeBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.arugal.example.grpc.gencode.HelloResponse hello(com.github.arugal.example.grpc.gencode.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HelloSericeFutureStub extends io.grpc.stub.AbstractStub<HelloSericeFutureStub> {
    private HelloSericeFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HelloSericeFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloSericeFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HelloSericeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.arugal.example.grpc.gencode.HelloResponse> hello(
        com.github.arugal.example.grpc.gencode.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HelloSericeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HelloSericeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          serviceImpl.hello((com.github.arugal.example.grpc.gencode.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.github.arugal.example.grpc.gencode.HelloResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HelloSericeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HelloSericeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.arugal.example.grpc.gencode.HelloSerive.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HelloSerice");
    }
  }

  private static final class HelloSericeFileDescriptorSupplier
      extends HelloSericeBaseDescriptorSupplier {
    HelloSericeFileDescriptorSupplier() {}
  }

  private static final class HelloSericeMethodDescriptorSupplier
      extends HelloSericeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HelloSericeMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HelloSericeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HelloSericeFileDescriptorSupplier())
              .addMethod(getHelloMethod())
              .build();
        }
      }
    }
    return result;
  }
}
