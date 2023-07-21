package org.rangiffler.test.ui;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.Selenide.open;
import static org.rangiffler.config.ConfigHub.configEnv;

public class LoginTest {

    @ApiLogin(
            username = "kzk",
            password = "kzk"
    )
    @Test
    void test1() {
        open(configEnv.frontUrl());
        System.out.println("#####################"+configEnv.frontUrl());

    }

    @GenerateUser(
            username = "newUser66",
            password = "newUser66"
    )
    @AllureId("1001")
    @Test
    void test2(UserJson userJson) {
        System.out.println(userJson);
    }
}
