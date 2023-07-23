package org.rangiffler.api.grpc;

import com.google.protobuf.Empty;
import guru.qa.grpc.rangiffler.grpc.UsernameRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.grpc.AllureGrpc;

public abstract class GrpcBaseClient {
    protected static final Empty EMPTY = Empty.getDefaultInstance();
    protected static Channel channel;
    public GrpcBaseClient(String grpcAddress, int grpcPort) {
        channel = ManagedChannelBuilder
                .forAddress(grpcAddress, grpcPort)
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    protected UsernameRequest getUsernameRequest(String username) {
        return UsernameRequest.newBuilder().setUsername(username).build();
    }
}
