package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.grpc.rangiffler.grpc.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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

}
