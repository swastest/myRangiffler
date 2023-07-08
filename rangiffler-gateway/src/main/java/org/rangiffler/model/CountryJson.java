package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import guru.qa.grpc.rangiffler.grpc.Country;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CountryJson {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    public static CountryJson convertFromGrpc(Country countryGrpc) {
        return new CountryJson(UUID.fromString(countryGrpc.getUuid()), countryGrpc.getCode(), countryGrpc.getName());
    }

    public Country convertToGrpc() {
        return Country.newBuilder().setCode(code)
                .setUuid(id != null ? id.toString() : "")
                .setName(name)
                .build();
    }
}
