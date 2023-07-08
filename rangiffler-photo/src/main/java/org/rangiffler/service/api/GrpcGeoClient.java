package org.rangiffler.service.api;

import com.google.protobuf.Empty;
import guru.qa.grpc.rangiffler.grpc.Country;
import guru.qa.grpc.rangiffler.grpc.CountryByCodeRequest;
import guru.qa.grpc.rangiffler.grpc.RangifflerCountriesServiceGrpc;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrpcGeoClient {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcGeoClient.class);
    private static final Empty EMPTY = Empty.getDefaultInstance();

    @GrpcClient("grpcCountriesClient")
    private RangifflerCountriesServiceGrpc.RangifflerCountriesServiceBlockingStub rangifflerCountriesServiceStub;

    public Country getCountryByCode(CountryByCodeRequest request) {
        return  rangifflerCountriesServiceStub.getCountriesByCode(request);
    }
}
