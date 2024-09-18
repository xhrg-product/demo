package com.github.xhrg.grpc.client;

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
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: demo.proto")
public final class SimpleGrpc {

  private SimpleGrpc() {}

  public static final String SERVICE_NAME = "com.github.xhrg.grpc.client2.Simple";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.xhrg.grpc.client.SimpleProto.MyRequest,
      com.github.xhrg.grpc.client.SimpleProto.MyResponse> getOneToOneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OneToOne",
      requestType = com.github.xhrg.grpc.client.SimpleProto.MyRequest.class,
      responseType = com.github.xhrg.grpc.client.SimpleProto.MyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xhrg.grpc.client.SimpleProto.MyRequest,
      com.github.xhrg.grpc.client.SimpleProto.MyResponse> getOneToOneMethod() {
    io.grpc.MethodDescriptor<com.github.xhrg.grpc.client.SimpleProto.MyRequest, com.github.xhrg.grpc.client.SimpleProto.MyResponse> getOneToOneMethod;
    if ((getOneToOneMethod = SimpleGrpc.getOneToOneMethod) == null) {
      synchronized (SimpleGrpc.class) {
        if ((getOneToOneMethod = SimpleGrpc.getOneToOneMethod) == null) {
          SimpleGrpc.getOneToOneMethod = getOneToOneMethod = 
              io.grpc.MethodDescriptor.<com.github.xhrg.grpc.client.SimpleProto.MyRequest, com.github.xhrg.grpc.client.SimpleProto.MyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.github.xhrg.grpc.client2.Simple", "OneToOne"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xhrg.grpc.client.SimpleProto.MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xhrg.grpc.client.SimpleProto.MyResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SimpleMethodDescriptorSupplier("OneToOne"))
                  .build();
          }
        }
     }
     return getOneToOneMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SimpleStub newStub(io.grpc.Channel channel) {
    return new SimpleStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SimpleBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SimpleBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SimpleFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SimpleFutureStub(channel);
  }

  /**
   */
  public static abstract class SimpleImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public void oneToOne(com.github.xhrg.grpc.client.SimpleProto.MyRequest request,
        io.grpc.stub.StreamObserver<com.github.xhrg.grpc.client.SimpleProto.MyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getOneToOneMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOneToOneMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xhrg.grpc.client.SimpleProto.MyRequest,
                com.github.xhrg.grpc.client.SimpleProto.MyResponse>(
                  this, METHODID_ONE_TO_ONE)))
          .build();
    }
  }

  /**
   */
  public static final class SimpleStub extends io.grpc.stub.AbstractStub<SimpleStub> {
    private SimpleStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SimpleStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SimpleStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SimpleStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public void oneToOne(com.github.xhrg.grpc.client.SimpleProto.MyRequest request,
        io.grpc.stub.StreamObserver<com.github.xhrg.grpc.client.SimpleProto.MyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getOneToOneMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SimpleBlockingStub extends io.grpc.stub.AbstractStub<SimpleBlockingStub> {
    private SimpleBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SimpleBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SimpleBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SimpleBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public com.github.xhrg.grpc.client.SimpleProto.MyResponse oneToOne(com.github.xhrg.grpc.client.SimpleProto.MyRequest request) {
      return blockingUnaryCall(
          getChannel(), getOneToOneMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SimpleFutureStub extends io.grpc.stub.AbstractStub<SimpleFutureStub> {
    private SimpleFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SimpleFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SimpleFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SimpleFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xhrg.grpc.client.SimpleProto.MyResponse> oneToOne(
        com.github.xhrg.grpc.client.SimpleProto.MyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getOneToOneMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ONE_TO_ONE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SimpleImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SimpleImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ONE_TO_ONE:
          serviceImpl.oneToOne((com.github.xhrg.grpc.client.SimpleProto.MyRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xhrg.grpc.client.SimpleProto.MyResponse>) responseObserver);
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

  private static abstract class SimpleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SimpleBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xhrg.grpc.client.SimpleProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Simple");
    }
  }

  private static final class SimpleFileDescriptorSupplier
      extends SimpleBaseDescriptorSupplier {
    SimpleFileDescriptorSupplier() {}
  }

  private static final class SimpleMethodDescriptorSupplier
      extends SimpleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SimpleMethodDescriptorSupplier(String methodName) {
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
      synchronized (SimpleGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SimpleFileDescriptorSupplier())
              .addMethod(getOneToOneMethod())
              .build();
        }
      }
    }
    return result;
  }
}
