package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static PhotoJson convertFromGrpc(Photo photoGrpc) {
        return PhotoJson.builder()
                .id(!photoGrpc.getId().isEmpty() ? UUID.fromString(photoGrpc.getId()) : null)
                .photo(photoGrpc.getPhoto())
                .countryJson(CountryJson.convertFromGrpc(photoGrpc.getCountry()))
                .description(photoGrpc.getDescription())
                .username(photoGrpc.getUsername())
                .build();
    }

    public Photo convertToGrpc() {
        return Photo.newBuilder()
                .setId(id != null ? id.toString() : "")
                .setUsername(username)
                .setDescription(description)
                .setPhoto(photo)
                .setCountry(countryJson.convertToGrpc())
                .build();
    }
}
