package org.rangiffler.page.component.module;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class PhotoModule {
    SelenideElement countryName = $("[data-testid=PlaceIcon]").parent();
    SelenideElement description = $("[data-testid=PlaceIcon]").parent().sibling(0);
    SelenideElement closeTub = $("[data-testid=CloseIcon]");

    @Step("Check countryName intoPhoto")
    public PhotoModule checkCountry(String expectCountryName) {
        countryName.shouldHave(Condition.text(expectCountryName));
        return this;
    }

    @Step("Check description intoPhoto")
    public PhotoModule checkDescription(String expectDescription) {
        description.shouldHave(Condition.text(expectDescription));
        return this;
    }

    @Step("Close PhotoInfo module")
    public  <T> T closePhotoInfo (T page) {
        closeTub.click();
        return page;
    }

}
