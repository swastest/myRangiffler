package org.rangiffler.page.component.headerModule;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.kafka.common.protocol.types.Field;
import org.rangiffler.page.component.UserHeader;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class ProfileModule extends UserHeader {

    private final SelenideElement firstnameInput = $("input[name='firstName']");
    private final SelenideElement lastnameInput = $("input[name='lastName']");
    private final SelenideElement submitButton = $("button[type='submit']");

    @Step("Set firstname in profile: {0}")
    public ProfileModule setFirstname(String firstname) {
        firstnameInput.setValue(firstname);
        return this;
    }

    @Step("Set lastname in profile: {0}")
    public ProfileModule setLastname(String lastname) {
        lastnameInput.setValue(lastname);
        return this;
    }

//    @Step("Click [Save] button")
//    public MainPage clickSave() {
//        submitButton.click();
//        return new MainPage();
//    }

    @Step("Check firstname in profile: {0}")
    public ProfileModule firstnameShouldBe(String firstname) {
        firstnameInput.shouldHave(value(firstname));
        return this;
    }

    @Step("Check lastname in profile: {0}")
    public ProfileModule lastnameShouldBe(String surname) {
        lastnameInput.shouldHave(value(surname));
        return this;
    }

    @Step("Check username in profile: {0}")
    public ProfileModule checkUserLoginName(String loginName) {
        $(byText("Username:")).shouldHave(text(loginName));
        return this;
    }

//    @Step("Check avatar")
//    public ProfileModule checkAvatar(final String avatar) {
//        $(".MuiAvatar-img").shouldHave(PhotoCondition.photo(avatar), ofSeconds(15L));
//        return this;
//    }
}
