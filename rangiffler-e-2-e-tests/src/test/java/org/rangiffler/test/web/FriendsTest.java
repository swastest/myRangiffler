package org.rangiffler.test.web;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;

public class FriendsTest extends BaseWebTest {
    @ApiLogin(
            user = @GenerateUser(
                    incomeInvitations = @Friend,
                    avatarPath = "Images/img1.jpeg",
                    photos = @Photo(
                            photoPath = "Images/img3.jpeg"
                    )
            )
    )
    @Test
    @AllureId("2001")
    void incomeInvitationsTest(UserJson userJson) {
        UserJson friend = userJson.getIncomeInvitations().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toPeopleAround().checkThatPageLoaded()
                .checkPeopleAround(friend, FriendStatus.INVITATION_RECEIVED);
    }

    @ApiLogin(
            user = @GenerateUser(
                    outcomeInvitations = @Friend,
                    avatarPath = "Images/img4.png",
                    photos = @Photo(
                            photoPath = "Images/img3.jpeg"
                    )
            )
    )
    @Test
    @AllureId("2002")
    void outcomeInvitationsTest(UserJson userJson) {
        UserJson friend = userJson.getOutcomeInvitations().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toPeopleAround().checkThatPageLoaded()
                .checkPeopleAround(friend, FriendStatus.INVITATION_SENT);
    }

    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend,
                    avatarPath = "Images/img2.jpeg",
                    photos = @Photo(
                            photoPath = "Images/img3.jpeg"
                    )
            )
    )
    @Test
    @AllureId("2003")
    void friendsTest(UserJson userJson) {
        UserJson friend = userJson.getFriends().get(0);
        yourTravelsPage.openTravelsPage(tabs)
                .toPeopleAround().checkThatPageLoaded()
                .checkPeopleAround(friend, FriendStatus.FRIEND);
    }

    @ApiLogin(
            user = @GenerateUser(
                    friends = @Friend,
                    avatarPath = "Images/img2.jpeg",
                    photos = @Photo(
                            photoPath = "Images/img3.jpeg"
                    )
            )
    )
    @Test
    @AllureId("2004")
    void friendsTableTest(UserJson userJson) {
        UserJson friend = userJson.getFriends().get(0);
        yourTravelsPage.openTravelsPage(header)
                .toFriends()
                .checkExistingFriends(friend);
    }
}
