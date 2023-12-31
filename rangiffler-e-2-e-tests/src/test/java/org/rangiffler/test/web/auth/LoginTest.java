package org.rangiffler.test.web.auth;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.component.UserHeader;
import org.rangiffler.test.web.BaseWebTest;
import org.rangiffler.utils.DataUtils;
import org.rangiffler.utils.ErrorMessage;

import static org.rangiffler.utils.testDoc.TestTag.*;

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
                .submit(new UserHeader())
                .toProfile()
                .checkUserLoginName(userJson.getUsername());
    }

    @Disabled
    @Test
    @AllureId("1002")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(AUTH)})
    @GenerateUser
    @DisplayName("Success login from RegisterPage")
    void positiveLoginFromRegisterPage(UserJson userJson) {
        registerPage.open()
                .toLogin()
                .fillAuthorizationForm(userJson.getUsername(), userJson.getPassword())
                .submit(new UserHeader())
                .toProfile()
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
