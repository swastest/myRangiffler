package org.rangiffler.jupiter.extension;

import com.codeborne.selenide.Selenide;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rangiffler.api.AuthRestClient;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.api.context.SessionStorageContext;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.utils.OauthUtils;

import static org.rangiffler.config.ConfigHub.configEnv;

public class ApiLoginExtension implements BeforeEachCallback, AfterTestExecutionCallback {

   private final AuthRestClient authRestClient = new AuthRestClient();
    private static final String JSESSIONID = "JSESSIONID";

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ApiLogin annotation = context.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (annotation != null) {
            doLogin(annotation.username(), annotation.password());
        }
    }

    private void doLogin(String username, String password) {
        final SessionStorageContext sessionStorageContext = SessionStorageContext.getInstance();

        final String codeVerifier = OauthUtils.generateCodeVerifier();
        final String codeChallange = OauthUtils.generateCodeChallange(codeVerifier);

        sessionStorageContext.setCodeChallange(codeChallange);
        sessionStorageContext.setCodeVerifier(codeVerifier);
        authRestClient.authorizePreRequest();
        authRestClient.login(username, password);
        final String token = authRestClient.getToken();
        Selenide.open(configEnv.frontUrl());
        Selenide.sessionStorage().setItem("id_token", token);
        Selenide.sessionStorage().setItem("codeChallenge", sessionStorageContext.getCodeChallange());
        Selenide.sessionStorage().setItem("codeVerifier", sessionStorageContext.getCodeVerifier());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        SessionStorageContext.getInstance().release();
        CookieContext.getInstance().release();
    }
}
