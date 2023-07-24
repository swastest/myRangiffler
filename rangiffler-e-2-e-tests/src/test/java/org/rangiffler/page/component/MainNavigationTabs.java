package org.rangiffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.page.PeopleAroundPage;
import org.rangiffler.page.TravelsPage;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainNavigationTabs extends BaseComponent {
    public MainNavigationTabs() {
        super($("[role=tablist]"));
    }
    private final SelenideElement travelsTub = self.$(byText("Your travels"));
    private final SelenideElement friendsTravelsTub = self.$(byText("Friends travels"));
    private final SelenideElement peopleAroundTub = self.$(byText("People Around"));

    @Override
    @Step("Check load MainNavigationTabs")
    public BaseComponent checkThatComponentDisplayed() {
        travelsTub.shouldBe(Condition.visible);
        friendsTravelsTub.shouldBe(Condition.visible);
        peopleAroundTub.shouldBe(Condition.visible);
        return this;
    }

    @Step("Go to 'Your travels'")
    public TravelsPage toYourTravels() {
        travelsTub.click();
        return new TravelsPage();
    }
    @Step("Go to 'Friends travels'")
    public TravelsPage toFriendsTravels() {
        friendsTravelsTub.click();
        return new TravelsPage();
    }
    @Step("Go to 'People Around'")
    public PeopleAroundPage toPeopleAround() {
        peopleAroundTub.click();
        return new PeopleAroundPage();
    }
}
