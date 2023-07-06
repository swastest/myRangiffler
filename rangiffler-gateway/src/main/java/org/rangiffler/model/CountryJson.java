package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import guru.qa.grpc.rangiffler.grpc.Country;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("code")
  private String code;

  @JsonProperty("name")
  private String name;

  public static CountryJson fromGrpcMessage(Country currencyMessage) {
    return new CountryJson(UUID.fromString(currencyMessage.getUuid()), currencyMessage.getCode(), currencyMessage.getName());
  }
}
