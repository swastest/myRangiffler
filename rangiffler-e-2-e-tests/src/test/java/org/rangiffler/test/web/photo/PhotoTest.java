package org.rangiffler.test.web.photo;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.UserJson;
import org.rangiffler.test.web.BaseWebTest;

import static org.rangiffler.utils.DataUtils.convertImage;
import static org.rangiffler.utils.testDoc.TestTag.*;

@DisplayName("WEB: Photo")
public class PhotoTest extends BaseWebTest {

    @Test
    @AllureId("3001")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(PHOTO)})
    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend(
                            photos = @Photo(
                                    photoPath = "images/img1.jpeg",
                                    countryCode = "RU",
                                    description = "Kazan Republic!!!"
                            )
                    ),
                    avatarPath = "images/img2.jpeg",
                    photos = @Photo(
                            photoPath = "images/img3.jpeg"
                    )
            )
    )
    @DisplayName("Friends photo is visible")
    void friendsPhotoTest(UserJson userJson) {
        UserJson friend = userJson.getFriends().get(0);
        String expectedResult = friend.getPhotos().get(0).getPhoto();
        yourTravelsPage.openTravelsPage(tabs)
                .toFriendsTravels()
                .checkFirstPhoto(expectedResult);
    }

    @AllureId("3002")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(PHOTO)})
    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend(
                            photos = @Photo(
                                    photoPath = "images/img1.jpeg",
                                    countryCode = "RU",
                                    description = "Kazan Republic!!!"
                            )
                    ),
                    avatarPath = "images/img2.jpeg"
            )
    )
    @CsvSource(value = {
            "images/img1.jpeg | Russia | HelloWorld",
            "images/img2.jpeg | Tanzania | TanzaniaHelloWorld"
    },
            delimiter = '|')
    @ParameterizedTest(name = "Add Photo Test")
    void addPhoto(String img, String country, String description1, UserJson userJson) {
        String imgPath = img;
        String expectedImg = convertImage(imgPath);
        String countryName = country;
        String description = description1;

        yourTravelsPage.openTravelsPage(header)
                .addPhoto(imgPath)
                .selectCountry(countryName)
                .setPhotoDescription(description)
                .savePhoto()
                .checkFirstPhoto(expectedImg)
                .toPhotoInfo()
                .checkCountry(countryName)
                .checkDescription(description);
    }
}
