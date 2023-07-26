package org.rangiffler.api.rest;


import org.junit.jupiter.api.Assertions;
import org.rangiffler.model.FriendJson;
import org.rangiffler.model.UserJson;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

import static org.rangiffler.config.ConfigHub.configEnv;

public class UserDataRestClient extends BaseRestClient {
    public UserDataRestClient() {
        super(configEnv.usersUrl());
    }

    UserDataService userDataService = retrofit.create(UserDataService.class);

    public @NonNull UserJson getCurrentUserInfo(String username) {
        try {
            return userDataService.currentUser(username).execute().body();
        } catch (IOException e) {
            Assertions.fail("unsuccessful connection to the service niffler-userData " + e.getMessage());
            return null;
        }
    }

    public @NonNull UserJson sendInviteFriend(String username, String friendUsername) {
        try {
            FriendJson friendJson = new FriendJson();
            friendJson.setUsername(friendUsername);
            return userDataService.sendInviteFriend(username, friendJson).execute().body();
        } catch (IOException e) {
            Assertions.fail("unsuccessful connection to the service niffler-userData " + e.getMessage());
            return null;
        }
    }

    public @NonNull UserJson acceptInvitation(String username, String inviteUsername) {
        try {
            FriendJson friendJson = new FriendJson();
            friendJson.setUsername(inviteUsername);
            return userDataService.acceptInvitation(username, friendJson).execute().body();
        } catch (IOException e) {
            Assertions.fail("unsuccessful connection to the service niffler-userData " + e.getMessage());
            return null;
        }
    }

    public UserJson updateUser(UserJson userJson) {
        try {
            return userDataService.updateUser(userJson).execute().body();
        } catch (IOException e) {
            Assertions.fail("unsuccessful connection to the service niffler-userData " + e.getMessage());
            return null;
        }
    }

    public List<UserJson> getAllUsers(String username) {
        try {
            return userDataService.getAllUsers(username).execute().body();
        } catch (IOException e) {
            Assertions.fail("unsuccessful connection to the service niffler-userData " + e.getMessage());
            return null;
        }
    }
}
