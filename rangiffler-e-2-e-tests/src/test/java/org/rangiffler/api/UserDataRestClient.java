package org.rangiffler.api;


import org.junit.jupiter.api.Assertions;
import org.rangiffler.model.UserJson;
import org.springframework.lang.NonNull;

import java.io.IOException;

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
            Assertions.fail("Не смогли подключиться к niffler-userData "+e.getMessage());
            return null;
        }
    }

}
