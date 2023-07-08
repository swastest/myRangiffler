package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import guru.qa.grpc.rangiffler.grpc.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("country")
  private CountryJson countryJson;

  @JsonProperty("photo")
  private String photo;

  @JsonProperty("description")
  private String description;

  @JsonProperty("username")
  private String username;

  public static PhotoJson convertFromGrpc(Photo photo) {
   return PhotoJson.builder()
            .id(UUID.fromString(photo.getId()))
            .photo(photo.getPhoto())
//            .countryJson(CountryJson.builder()
//                    .id(UUID.fromString(photo.getCountry().getUuid()))
//                    .code(photo.getCountry().getCode())
//                    .name(photo.getCountry().getName())
//                    .build())
           .countryJson(CountryJson.convertFromGrpc(photo.getCountry()))
            .description(photo.getDescription())
            .username(photo.getUsername())
            .build();
  }

  public Photo convertToGrpc() {
   return Photo.newBuilder()
            .setId(id!=null?id.toString():"")
            .setUsername(username)
            .setDescription(description)
            .setPhoto(photo)
            .setCountry(countryJson.convertToGrpc())
            .build();
  }
}
