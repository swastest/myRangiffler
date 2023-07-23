package org.rangiffler.test.web;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.LoginPage;
import org.rangiffler.utils.DataUtils;
import org.rangiffler.utils.ErrorMessage;

import static org.rangiffler.testDoc.TestTag.*;

@DisplayName("WEB: Authorization")
public class LoginTest extends BaseWebTest {

    @Test
    @AllureId("1001")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(AUTH)})
    @GenerateUser
    @DisplayName("Success login from LandingPage")
    void positiveLoginFromLandingPage(UserJson userJson) {
        landingPage.open()
                .toLogin()
                .fillAuthorizationForm(userJson.getUsername(), userJson.getPassword())
                .submit(new LoginPage()); //main
        header.toProfile()
                .checkUserLoginName(userJson.getUsername());
    }

    @Test
    @AllureId("1002")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(AUTH)})
    @GenerateUser
    @DisplayName("Success login from RegisterPage")
    void positiveLoginFromRegisterPage(UserJson userJson) {
        registerPage.open()
                .toLogin()
                .fillAuthorizationForm(userJson.getUsername(), userJson.getPassword())
                .submit(new LoginPage()); //main
        header.toProfile()
                .checkUserLoginName(userJson.getUsername());
    }

    @Test
    @AllureId("1003")
    @Tags({@Tag(WEB), @Tag(NEGATIVE), @Tag(AUTH)})
    @DisplayName("Negative case login - check error message")
    void negativeLogin() {
        landingPage.open()
                .toLogin()
                .fillAuthorizationForm(DataUtils.generateRandomUsername(), DataUtils.generateRandomPassword())
                .submit(new LoginPage())
                .checkErrorMessage(ErrorMessage.BAD_CREDENTIALS);
    }
}
