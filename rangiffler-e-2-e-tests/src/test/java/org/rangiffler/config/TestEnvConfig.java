package org.rangiffler.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/${test.env}.properties")
public interface TestEnvConfig extends Config {
    @Key("front.url")
    String frontUrl();
    @Key("auth.url")
    String authUrl();
    @Key("users.url")
    String usersUrl();
    @Key("users.grpc.address")
    String usersGrpcAddress();
    @Key("users.grpc.port")
    Integer usersGrpcPort();
    @Key("geo.grpc.address")
    String geoGrpcAddress();
    @Key("geo.grpc.port")
    Integer geosGrpcPort();
    @Key("photo.grpc.address")
    String photoGrpcAddress();
    @Key("photo.grpc.port")
    Integer photosGrpcPort();
    @Key("gateway.url")
    String gatewayUrl();
    @Key("db.address")
    String dbAddress();
    @Key("db.user")
    String dbLogin();
    @Key("db.password")
    String dbPassword();
    @Key("kafka.address")
    String kafkaAddress();

}
