package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.utils.ErrorMessage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.rangiffler.config.ConfigHub.configEnv;

public class RegisterPage extends BasePage<RegisterPage> {

    public static final String URL = configEnv.authUrl() + "/register";

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement passwordSubmit = $("input[name='passwordSubmit']");

    @Step("Input username: {0}")
    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Input password: {0}")
    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Confirm password: {0}")
    public RegisterPage confirmPassword(String password) {
        passwordSubmit.setValue(password);
        return this;
    }

    @Step("Submit registration")
    public RegisterPage submit() {
        $(".form__submit").click();
        return new RegisterPage();
    }
    @Step("Open 'Register page'")
    public RegisterPage open() {
        return Selenide.open(URL, RegisterPage.class);
    }


    @Step("Go to user login")
    public LoginPage toLogin() {
        $("a[href*='redirect']").click();
        return new LoginPage();
    }

    @Step("Check error message: {0}")
    public RegisterPage checkError(ErrorMessage errorText) {
        $(".form__error").shouldHave(text(errorText.content));
        return this;
    }

    @Override
    @Step("Check load RegisterPage")
    public RegisterPage checkThatPageLoaded() {
        usernameInput.shouldBe(Condition.visible);
        passwordInput.shouldBe(Condition.visible);
        return this;
    }
}
