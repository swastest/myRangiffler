package org.rangiffler.page.component.headerModule;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.rangiffler.condition.friends.FriendsCondition.friends;

public class FriendsModule {

    private final SelenideElement friendsTable = $("table[aria-label='friends table']");

    @Step("Checking existing friends: {0}")
    public FriendsModule checkExistingFriends(UserJson...userJsons) {
        friendsTable.$$(byTagName("tr"))
                .shouldHave(friends(userJsons));
        return this;
    }

    @Step("Remove friend by name {0}")
    public FriendsModule removeFriendByName(String name) {

        $$(byTagName("tr"))
                .findBy(text(name))
                .find("button[aria-label='Remove friend']")
                .click();
        $("button[type='submit']").should(appear).click();
        return this;
    }
}
