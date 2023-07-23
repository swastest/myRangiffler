package org.rangiffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.utils.ErrorMessage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.rangiffler.config.ConfigHub.configEnv;

public class LoginPage extends BasePage<LoginPage> {

    public static final String URL = configEnv.authUrl() + "/login";
    private final SelenideElement usernameInput = $("input[name=username]");
    private final SelenideElement passwordInput = $("input[name=password]");
    private final SelenideElement submitButton = $("button[type=submit]");
    private final SelenideElement registerLink = $("a[href='/register']");

    private final SelenideElement errorMessage = $(".form__error");

    @Step("Submit login")
    public <T extends BasePage> T submit(T expectedPage) {
        submitButton.click();
        return expectedPage;
    }

    @Step("Authorization: username: {0}, password: {1}")
    public LoginPage fillAuthorizationForm(String login, String password) {
        enterLogin(login);
        enterPassword(password);
        return this;
    }

    @Step("Set login for authorization: {0}")
    public LoginPage enterLogin(String login) {
        usernameInput.setValue(login);
        return this;
    }

    @Step("Set password for authorization: {0}")
    public LoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Open 'Login page'")
    public LoginPage open() {
        return Selenide.open(URL, LoginPage.class);
    }

    @Step("Click Register button")
    public RegisterPage toRegister() {
        $("a[href*='register']").click();
        return new RegisterPage();
    }

    public LoginPage checkErrorMessage(ErrorMessage message) {
        errorMessage.shouldHave(text(message.content));
        return this;
    }

    @Override
    @Step("Check load LoginPage")
    public LoginPage checkThatPageLoaded() {
        usernameInput.should(visible);
        passwordInput.should(visible);
        return this;
    }

}
