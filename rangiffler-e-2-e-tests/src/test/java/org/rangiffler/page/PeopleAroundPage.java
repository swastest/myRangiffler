package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class PeopleAroundPage extends BasePage<PeopleAroundPage> {
    private final ElementsCollection table = $$("table[aria-label='all people table']");
    private final ElementsCollection peopleTable = $$("tbody tr");

    @Override
    public PeopleAroundPage checkThatPageLoaded() {
        table.get(0).shouldBe(Condition.visible);
        return this;
    }

    @Step("Check friendStatus")
    public PeopleAroundPage checkPeopleAround(UserJson friend, FriendStatus status) {
        SelenideElement result = peopleTable.filter(Condition.text(friend.getUsername()))
                .shouldHave(sizeGreaterThan(0)).get(0);
        result.$$("td").get(1).shouldHave(exactTextCaseSensitive(friend.getUsername()));
        if (status.text.equals(FriendStatus.FRIEND)) {
            result.$$("td").get(3).shouldBe(attribute("aria-label", "Remove friend"));
        } else if (status.text.equals(FriendStatus.NOT_FRIEND)) {
            result.$$("td").get(3).shouldBe(attribute("aria-label", "Add friend"));
        } else if (status.text.equals(FriendStatus.INVITATION_SENT)) {
            result.$$("td").get(3).shouldBe(text("Invitation sent"));
        } else if (status.text.equals(FriendStatus.INVITATION_RECEIVED)){
            result.$$("td").get(3).shouldBe(attribute("aria-label", "Accept invitation"));
            result.$$("td").get(3).shouldBe(attribute("aria-label", "Decline invitation"));
        }
        return this;
    }
}
