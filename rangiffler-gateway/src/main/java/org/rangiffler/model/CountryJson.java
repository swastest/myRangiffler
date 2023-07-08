package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import guru.qa.grpc.rangiffler.grpc.Country;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryJson {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    public static CountryJson convertFromGrpc(Country countryGrpc) {
        String uuidString = countryGrpc.getUuid();
        UUID uuid = null;
        if (uuidString != null && !uuidString.isEmpty()) {
            uuid = UUID.fromString(uuidString);
        }
        return new CountryJson(uuid, countryGrpc.getCode(), countryGrpc.getName());
    }

    public Country convertToGrpc() {
        return Country.newBuilder().setCode(code)
                .setUuid(id != null ? id.toString() : "")
                .setName(name)
                .build();
    }
}
