package org.rangiffler.page;

import static com.codeborne.selenide.Selenide.open;
import static org.rangiffler.config.ConfigHub.configEnv;

public class YourTravelsPage extends BasePage<YourTravelsPage> {

    public static final String URL = configEnv.frontUrl();


    @Override
    public YourTravelsPage checkThatPageLoaded() {
        return null;
    }

    public <T> T openTravelsPage(T page) {
        open(URL, YourTravelsPage.class);
        return page;
    }

}
