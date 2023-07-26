package org.rangiffler.test.api.grpc.photo;

import guru.qa.grpc.rangiffler.grpc.Photos;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import org.rangiffler.db.entity.photo.PhotoEntity;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.test.api.BaseGrpcTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rangiffler.utils.testDoc.TestTag.*;

public class PhotoServiceTest extends BaseGrpcTest {
    @Test
    @AllureId("6001")
    @Tags({@Tag(API), @Tag(GRPC), @Tag(POSITIVE), @Tag(PHOTO)})
    @GenerateUser(
            photos = {
                    @Photo(
                            photoPath = "images/img4.png",
                            description = "HelloMexico",
                            countryCode = "MX"
                    ),
                    @Photo(
                            photoPath = "images/img3.jpeg",
                            description = "HelloWorld",
                            countryCode = "GL"
                    )
            }
    )
    @DisplayName("Get all current user photos")
    void getPhotoForCurrentUser(UserJson userJson) {
        List<PhotoJson> expectPhotos = userJson.getPhotos();
        List<PhotoJson> actualPhotos = new ArrayList<>();
        Photos userPhotos = photoGrpcClient.getUserPhotos(userJson.getUsername());
        for (guru.qa.grpc.rangiffler.grpc.Photo photo : userPhotos.getPhotosList()) {
            actualPhotos.add(PhotoJson.convertFromGrpc(photo));
        }
        step("Assertions response", () -> {
            assertEquals(expectPhotos.size(), actualPhotos.size(), "Check count photos");
            assertThat(actualPhotos, containsInAnyOrder(expectPhotos.toArray()));
        });
    }

    @Test
    @AllureId("6002")
    @Tags({@Tag(API), @Tag(GRPC), @Tag(POSITIVE), @Tag(PHOTO)})
    @GenerateUser(
            photos = {
                    @Photo(
                            photoPath = "images/img4.png",
                            description = "HelloMexico",
                            countryCode = "MX"
                    )
            }
    )
    @DisplayName("Edit photo")
    void editPhotoTest(UserJson userJson) {
        PhotoEntity photoEntity = photoDao.findPhotoByUsername(userJson.getUsername());
        PhotoJson editPhoto = userJson.getPhotos().get(0);
        editPhoto.setDescription("new description");
        editPhoto.setId(photoEntity.getId());
        PhotoJson photoAfterEdit = photoGrpcClient.editPhoto(userJson.getPhotos().get(0));
        step("Assertions response", () -> {
            assertEquals(editPhoto, photoAfterEdit, "Check modified photo");
        });
    }

    @Test
    @AllureId("6003")
    @Tags({@Tag(API), @Tag(GRPC), @Tag(POSITIVE), @Tag(PHOTO)})
    @GenerateUser(
            friends = {
                    @Friend(photos = @Photo(
                            photoPath = "images/img4.png"
                    )),
                    @Friend(photos = @Photo(
                            photoPath = "images/img1.jpeg"
                    )),
                    @Friend(photos = @Photo(
                            photoPath = "images/img2.jpeg"
                    ))
            }

    )
    @DisplayName("Get friends photo")
    void getFriendsPhoto(UserJson userJson) {
        List<UserJson> friends = userJson.getFriends();
        List<PhotoJson> expectedFriendsPhotos = friends.stream()
                .flatMap(friend -> friend.getPhotos().stream())
                .collect(Collectors.toList());
        List<PhotoJson> actualPhotos = new ArrayList<>();
        Photos userPhotos = photoGrpcClient.getAllFriendsPhotos(userJson.getUsername());
        for (guru.qa.grpc.rangiffler.grpc.Photo photo : userPhotos.getPhotosList()) {
            actualPhotos.add(PhotoJson.convertFromGrpc(photo));
        }
        step("Assertions response", () -> {
            assertEquals(expectedFriendsPhotos.size(), actualPhotos.size(), "Check count photos");
            assertThat(actualPhotos, containsInAnyOrder(expectedFriendsPhotos.toArray()));
        });
    }
}
