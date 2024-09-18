package com.github.xhrg.grpc.provider;

import com.github.xhrg.grpc.client.SimpleGrpc;
import com.github.xhrg.grpc.client.SimpleProto.MyRequest;
import com.github.xhrg.grpc.client.SimpleProto.MyResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServer extends SimpleGrpc.SimpleImplBase {

    public void oneToOne(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        MyResponse response = MyResponse.newBuilder().setMessage(request.getName() + "追加服务端信息").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}