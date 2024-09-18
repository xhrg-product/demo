package com.github.xhrg.grpc.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xhrg.grpc.client.SimpleGrpc;
import com.github.xhrg.grpc.client.SimpleProto.MyRequest;
import com.github.xhrg.grpc.client.SimpleProto.MyResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;

@RestController
@RequestMapping("grpc")
public class GrpcController {

    @GrpcClient("demo-grpc")
    private SimpleGrpc.SimpleBlockingStub service;

    @GetMapping("v1")
    public String getOneToOne() {
        MyRequest request = MyRequest.newBuilder().setName("来自client-v1").build();
        MyResponse response = service.oneToOne(request);
        return response.getMessage();
    }

    @GetMapping("v2")
    public String testForAddress() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        MyRequest request = MyRequest.newBuilder().setName("来自client-v2").build();
        SimpleGrpc.SimpleBlockingStub stub = SimpleGrpc.newBlockingStub(channel);
        return stub.oneToOne(request).getMessage();
    }
}