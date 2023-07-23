package org.rangiffler.test.web;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.UserJson;

import static org.rangiffler.config.ConfigHub.configEnv;

public class FriendsTest extends BaseWebTest {


    @ApiLogin(
            user = @GenerateUser(
                    username = "gogo",
                    password = "gogo",
                    incomeInvitations = @Friend,
                    avatarPath = "Images/img1.jpeg",
                    photos = @Photo(
                            photoPath = "Images/img3.jpeg"
                    )
            )
    )
    @Test
    @AllureId("2001")
    void test(UserJson userJson) {
        userJson.getFriends();
        Selenide.open(configEnv.frontUrl());
         int a =0;
    }

    @ApiLogin(username = "kzk",
    password = "kzk")
    @Test
    @AllureId("2002")
    void test2() {
        Selenide.open(configEnv.frontUrl());
        int a =0;
    }
}
