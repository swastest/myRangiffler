package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import guru.qa.grpc.rangiffler.grpc.Photo;
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
public class PhotoJson {

    private UUID id;

    private CountryJson countryJson;

    private String photo;

    private String description;

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
