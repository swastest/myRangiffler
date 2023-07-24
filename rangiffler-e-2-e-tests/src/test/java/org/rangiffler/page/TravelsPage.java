package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.rangiffler.page.component.module.PhotoModule;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.rangiffler.config.ConfigHub.configEnv;
import static org.rangiffler.utils.DataUtils.convertImage;

public class TravelsPage extends BasePage<TravelsPage> {

    public static final String URL = configEnv.frontUrl();

    SelenideElement firstPhoto = $("img.photo__list-item");

    @Override
    public TravelsPage checkThatPageLoaded() {
        return null;
    }

    public <T> T openTravelsPage(T page) {
        open(URL, TravelsPage.class);
        return page;
    }

    @Step("Check first photo")
    public TravelsPage checkFirstPhoto(String pathImgFromResources) {
        String expectImage = pathImgFromResources;
        String actualImage = firstPhoto.getAttribute("src");
        Assertions.assertEquals(expectImage, actualImage);
        return this;
    }

    @Step("Go to PhotoInfo module ")
    public PhotoModule toPhotoInfo() {
        firstPhoto.click();
        return new PhotoModule();
    }

}
