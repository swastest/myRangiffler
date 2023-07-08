package org.rangiffler.service.api;

import com.google.protobuf.Empty;
import guru.qa.grpc.rangiffler.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.rangiffler.model.PhotoJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static org.rangiffler.model.PhotoJson.convertFromGrpc;

@Component
public class GrpcPhotoClient {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcPhotoClient.class);
    private static final Empty EMPTY = Empty.getDefaultInstance();

    @GrpcClient("grpcPhotoClient")
    private RangifflerPhotoServiceGrpc.RangifflerPhotoServiceBlockingStub rangifflerPhotoServiceBlockingStub;

    public List<PhotoJson> getPhotosForUser(String username) {
        Photos photosForUser = rangifflerPhotoServiceBlockingStub
                .getPhotosForUser(UsernameRequest.newBuilder()
                        .setUsername(username).build());
        return photosForUser.getPhotosList().stream().map(PhotoJson::convertFromGrpc).toList();
    }

    public List<PhotoJson> getAllFriendsPhotos (String username) {
        Photos friendsPhoto = rangifflerPhotoServiceBlockingStub
                .getAllFriendsPhotos(UsernameRequest.newBuilder()
                        .setUsername(username).build());
        return friendsPhoto.getPhotosList().stream().map(PhotoJson::convertFromGrpc).toList();
    }

    public PhotoJson addPhoto(PhotoJson photoJson) {
        Photo photo = rangifflerPhotoServiceBlockingStub.addNewPhoto(photoJson.convertToGrpc());
       return convertFromGrpc(photo);
    }

    public PhotoJson editPhoto(PhotoJson photoJson) {
        Photo photo = rangifflerPhotoServiceBlockingStub.editPhoto(photoJson.convertToGrpc());
        return convertFromGrpc(photo);
    }

    public void deletePhoto(UUID photoId) {
        rangifflerPhotoServiceBlockingStub.deletePhoto(
                PhotoIdRequest.newBuilder()
                        .setId(photoId.toString())
                        .build());
    }
}
