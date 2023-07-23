package org.rangiffler.api.grpc;

import guru.qa.grpc.rangiffler.grpc.Country;
import guru.qa.grpc.rangiffler.grpc.CountryByCodeRequest;
import guru.qa.grpc.rangiffler.grpc.RangifflerCountriesServiceGrpc;

import static org.rangiffler.config.ConfigHub.configEnv;

public class CountryGrpcClient extends GrpcBaseClient {
    private RangifflerCountriesServiceGrpc.RangifflerCountriesServiceBlockingStub geoBlockingStub;
    public CountryGrpcClient() {
        super(configEnv.geoGrpcAddress(), configEnv.geosGrpcPort());
        geoBlockingStub = RangifflerCountriesServiceGrpc.newBlockingStub(channel);
    }

    public Country getCountryByCode(CountryByCodeRequest request) {
       return geoBlockingStub.getCountriesByCode(request);
    }
}
