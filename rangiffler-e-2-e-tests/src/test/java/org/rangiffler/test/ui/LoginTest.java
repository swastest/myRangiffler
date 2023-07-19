package org.rangiffler.test.ui;

import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;

import static com.codeborne.selenide.Selenide.open;
import static org.rangiffler.config.ConfigHub.configEnv;

public class LoginTest {

    @ApiLogin(
            username = "kzk",
            password = "kzk"
    )
    @Test
    void test1() {
        System.out.println("#####################"+configEnv.frontUrl());
        open(configEnv.frontUrl());
    }
}
