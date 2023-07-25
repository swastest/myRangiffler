package org.rangiffler.test.web.users;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;
import org.rangiffler.test.web.BaseWebTest;

import static org.rangiffler.utils.testDoc.TestTag.*;

@DisplayName("WEB: Friends")
public class FriendsTest extends BaseWebTest {

    @Test
    @AllureId("2001")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(USERS)})
    @ApiLogin(
            user = @GenerateUser(
                    incomeInvitations = @Friend,
                    avatarPath = "images/img1.jpeg",
                    photos = @Photo(
                            photoPath = "images/img3.jpeg"
                    )
            )
    )
    @DisplayName("incomeInvitations is visible")
    void incomeInvitationsTest(UserJson userJson) {
        UserJson friend = userJson.getIncomeInvitations().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toPeopleAround().checkThatPageLoaded()
                .checkPeopleAround(friend, FriendStatus.INVITATION_RECEIVED);
    }

    @Test
    @AllureId("2002")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(USERS)})
    @ApiLogin(
            user = @GenerateUser(
                    outcomeInvitations = @Friend,
                    avatarPath = "images/img4.png",
                    photos = @Photo(
                            photoPath = "images/img3.jpeg"
                    )
            )
    )
    @DisplayName("outcomeInvitations is visible")
    void outcomeInvitationsTest(UserJson userJson) {
        UserJson friend = userJson.getOutcomeInvitations().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toPeopleAround().checkThatPageLoaded()
                .checkPeopleAround(friend, FriendStatus.INVITATION_SENT);
    }

    @Test
    @AllureId("2003")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(USERS)})
    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend,
                    avatarPath = "images/img2.jpeg",
                    photos = @Photo(
                            photoPath = "images/img3.jpeg"
                    )
            )
    )
    @DisplayName("friends is visible")
    void friendsTest(UserJson userJson) {
        UserJson friend = userJson.getFriends().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toPeopleAround().checkThatPageLoaded()
                .checkPeopleAround(friend, FriendStatus.FRIEND);
    }

    @Test
    @AllureId("2004")
    @Tags({@Tag(WEB), @Tag(POSITIVE), @Tag(USERS)})
    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend,
                    avatarPath = "images/img2.jpeg",
                    photos = @Photo(
                            photoPath = "images/img3.jpeg"
                    )
            )
    )
    @DisplayName("friends is visible")
    void friendsTableTest(UserJson userJson) {
        UserJson friend = userJson.getFriends().get(0);
        yourTravelsPage.openTravelsPage(header)
                .toFriends()
                .checkExistingFriends(friend);
    }
}
