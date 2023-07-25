package org.rangiffler.test.api.grpc;

import org.rangiffler.api.grpc.CountryGrpcClient;
import org.rangiffler.api.grpc.PhotoGrpcClient;
import org.rangiffler.jupiter.annotation.GrpcTest;

@GrpcTest
public abstract class BaseGrpcTest {
    protected CountryGrpcClient countryGrpcClient = new CountryGrpcClient();
    protected PhotoGrpcClient photoGrpcClient = new PhotoGrpcClient();

}
