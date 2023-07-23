package org.rangiffler.api.grpc;

import guru.qa.grpc.rangiffler.grpc.Photo;
import guru.qa.grpc.rangiffler.grpc.PhotoIdRequest;
import guru.qa.grpc.rangiffler.grpc.RangifflerPhotoServiceGrpc;

import static org.rangiffler.config.ConfigHub.configEnv;

public class PhotoGrpcClient extends GrpcBaseClient{
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

}
