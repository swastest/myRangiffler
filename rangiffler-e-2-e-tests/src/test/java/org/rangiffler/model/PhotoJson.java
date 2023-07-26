package org.rangiffler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import guru.qa.grpc.rangiffler.grpc.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoJson photoJson = (PhotoJson) o;
        return Objects.equals(countryJson, photoJson.countryJson) && Objects.equals(photo, photoJson.photo) && Objects.equals(description, photoJson.description) && Objects.equals(username, photoJson.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryJson, photo, description, username);
    }
}
