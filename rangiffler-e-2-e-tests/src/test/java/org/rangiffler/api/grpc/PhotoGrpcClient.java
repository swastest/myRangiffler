package org.rangiffler.api.grpc;

import guru.qa.grpc.rangiffler.grpc.*;
import org.rangiffler.model.PhotoJson;

import static org.rangiffler.config.ConfigHub.configEnv;

public class PhotoGrpcClient extends GrpcBaseClient {
    private RangifflerPhotoServiceGrpc.RangifflerPhotoServiceBlockingStub photoBlockingStub;

    public PhotoGrpcClient() {
        super(configEnv.photoGrpcAddress(), configEnv.photosGrpcPort());
        photoBlockingStub = RangifflerPhotoServiceGrpc.newBlockingStub(channel);
    }

    public Photo addPhoto(Photo photo) {
        return photoBlockingStub.addNewPhoto(photo);
    }

    public void deletePhoto(PhotoIdRequest photoIdRequest) {
        photoBlockingStub.deletePhoto(photoIdRequest);
    }

    public Photos getAllFriendsPhotos(String Username) {
        return photoBlockingStub.getAllFriendsPhotos(getUsernameRequest(Username));
    }

    public Photos getUserPhotos(String Username) {
        return photoBlockingStub.getPhotosForUser(getUsernameRequest(Username));
    }

    public PhotoJson editPhoto(PhotoJson photoJson) {
        Photo photo = photoBlockingStub.editPhoto(photoJson.convertToGrpc());
        return PhotoJson.convertFromGrpc(photo);
    }

}
