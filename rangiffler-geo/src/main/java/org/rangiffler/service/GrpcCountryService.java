package org.rangiffler.service;

import com.google.protobuf.Empty;
import guru.qa.grpc.rangiffler.grpc.Countries;
import guru.qa.grpc.rangiffler.grpc.Country;
import guru.qa.grpc.rangiffler.grpc.CountryByCodeRequest;
import guru.qa.grpc.rangiffler.grpc.RangifflerCountriesServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class GrpcCountryService extends RangifflerCountriesServiceGrpc.RangifflerCountriesServiceImplBase {
    private final CountryRepository countryRepository;

    @Autowired
    public GrpcCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void getCountries(Empty request, StreamObserver<Countries> responseObserver) {
        List<CountryEntity> allCountries = countryRepository.findAll();

        Countries response = Countries.newBuilder()
                .addAllAllCountries(
                        allCountries.stream().map(
                                countryEntity -> Country.newBuilder()
                                        .setCode(countryEntity.getCode())
                                        .setName(countryEntity.getName())
                                        .setUuid(countryEntity.getId().toString())
                                        .build()
                        ).toList()
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCountriesByCode(CountryByCodeRequest request, StreamObserver<Country> responseObserver) {
        CountryEntity countryEntity = countryRepository.findByCode(request.getCode());

        Country response = Country.newBuilder()
                .setUuid(countryEntity.getCode())
                .setName(countryEntity.getName())
                .setCode(countryEntity.getCode())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
