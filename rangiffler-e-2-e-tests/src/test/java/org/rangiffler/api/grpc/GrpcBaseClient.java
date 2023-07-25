package org.rangiffler.api.grpc;

import guru.qa.grpc.rangiffler.grpc.UsernameRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.grpc.AllureGrpc;

public abstract class GrpcBaseClient {
    private static final int MAX_MESSAGE_SIZE = 50_000_000;
    protected static Channel channel;
    public GrpcBaseClient(String grpcAddress, int grpcPort) {
        channel = ManagedChannelBuilder
                .forAddress(grpcAddress, grpcPort)
                .maxInboundMessageSize(MAX_MESSAGE_SIZE)
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    protected UsernameRequest getUsernameRequest(String username) {
        return UsernameRequest.newBuilder().setUsername(username).build();
    }
}
