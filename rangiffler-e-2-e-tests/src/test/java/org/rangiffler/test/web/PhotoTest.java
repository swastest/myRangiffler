package org.rangiffler.test.web;

import com.codeborne.selenide.Condition;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;

public class PhotoTest extends BaseWebTest{
    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend(
                            photos = @Photo(
                                    photoPath = "Images/img1.jpeg",
                                    countryCode = "RU",
                                    description = "Kazan Republic!!!"
                            )
                    ),
                    avatarPath = "Images/img2.jpeg",
                    photos = @Photo(
                            photoPath = "Images/img3.jpeg"
                    )
            )
    )
    @Test
    @AllureId("3001")
    void friendsTableTest(UserJson userJson) {
        UserJson friend = userJson.getFriends().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toFriendsTravels().checkThatComponentDisplayed();
       String actualResult = $("img.photo__list-item").getAttribute("src");
       String expectedResult = friend.getPhotos().get(0).getPhoto();
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
