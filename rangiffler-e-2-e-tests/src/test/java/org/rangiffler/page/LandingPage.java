package org.rangiffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.rangiffler.config.ConfigHub.configEnv;

public class LandingPage extends BasePage<LandingPage> {
    private final SelenideElement loginButton = $(byText("Login"));
    private final SelenideElement registerButton = $(byText("Register"));
    @Override
    @Step("Check load LandingPage")
    public LandingPage checkThatPageLoaded() {
        loginButton.should(visible);
        registerButton.should(visible);
        return this;
    }

    @Step("Open LandingPage")
    public LandingPage open() {
        return Selenide.open(configEnv.frontUrl(), LandingPage.class);
    }

    @Step("Click Login button")
    public LoginPage toLogin() {
        loginButton.click();
        return new LoginPage();
    }

    @Step("Click Register button")
    public RegisterPage toRegistration() {
        registerButton.click();
        return new RegisterPage();
    }
}
