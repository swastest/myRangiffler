package org.rangiffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.page.TravelsPage;
import org.rangiffler.page.component.module.headerModule.FriendsModule;
import org.rangiffler.page.component.module.headerModule.ProfileModule;

import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class UserHeader extends BaseComponent<UserHeader> {
    public UserHeader() {
        super($(byTagName("header")));
    }

    private final SelenideElement addPhotoButton = self.$(byText("Add photo"));
    private final SelenideElement profileIcon = self.$(".MuiAvatar-circular");
    private final SelenideElement friendsIcon = self.$("div[aria-label='Your friends']");

    @Step("Click Add photo button Add photo: {0}")
    public UserHeader addPhoto(String resource) {
        addPhotoButton.click();
        $("#file").uploadFromClasspath(resource);
        return this;
    }

    @Step("Click Save photo button")
    public TravelsPage savePhoto() {
        $("button[type='submit']").click();
        return new TravelsPage();
    }

    @Step("Set description: {0}")
    public UserHeader setPhotoDescription(String description) {
        $$(byTagName("textarea")).get(0).setValue(description);
        return this;
    }

    @Step("Select country: {0}")
    public UserHeader selectCountry(String country) {
        $("div[role='button']").click();
        $(byText(country)).click();
        return this;
    }

    @Override
    @Step("Check load header")
    public UserHeader checkThatComponentDisplayed() {
        $(".MuiTypography-root").shouldHave(Condition.text("Rangiffler"));
        return this;
    }

    @Step("Go to user profile")
    public ProfileModule toProfile() {
        profileIcon.click();
        return new ProfileModule();
    }

    @Step("Go to user friends")
    public FriendsModule toFriends() {
        friendsIcon.click();
        return new FriendsModule();
    }

}
