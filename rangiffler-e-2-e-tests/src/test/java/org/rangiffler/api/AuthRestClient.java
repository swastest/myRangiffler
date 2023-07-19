package org.rangiffler.api;


import org.rangiffler.api.context.CookieContext;
import org.rangiffler.api.context.SessionStorageContext;
import org.rangiffler.api.interceptor.AddCookiesInterceptor;
import org.rangiffler.api.interceptor.RecievedCodeInterceptor;
import org.rangiffler.api.interceptor.RecievedCookiesInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.rangiffler.config.ConfigHub.configEnv;


public class AuthRestClient extends BaseRestClient {
    public AuthRestClient() {
        super(configEnv.authUrl(),
                true,
                new RecievedCookiesInterceptor(),
                new AddCookiesInterceptor(),
                new RecievedCodeInterceptor()
        );
    }

    private final AuthService authService = retrofit.create(AuthService.class);

    public void authorizePreRequest() {
        try {
            authService.authorized(
                    "code",
                    "client",
                    "openid",
                    configEnv.frontUrl() + "/authorized",
                    SessionStorageContext.getInstance().getCodeChallange(),
                    "S256"
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(String username, String password) {
        final CookieContext cookieContext = CookieContext.getInstance();
        try {
            authService.login(
                    cookieContext.getFormattedCookie("JSESSIONID"),
                    cookieContext.getFormattedCookie("XSRF-TOKEN"),
                    cookieContext.getCookie("XSRF-TOKEN"),
                    username, password
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getToken() {
        SessionStorageContext sessionStorageContext = SessionStorageContext.getInstance();
        try {
            return authService.token(
                    "Basic "
                            + Base64.getEncoder().encodeToString("client:secret".getBytes(StandardCharsets.UTF_8)),
                    "client",
                    configEnv.frontUrl() + "/authorized",
                    "authorization_code",
                    sessionStorageContext.getCode(),
                    sessionStorageContext.getCodeVerifier()
            ).execute().body().get("id_token").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getRegistrationToken() {
        try {
            authService.getRegistrationToken().execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doRegistration(String username, String password, String passwordSubmit) {
        final CookieContext cookieContext = CookieContext.getInstance();
        try {
            authService.doRegistration(
                    cookieContext.getFormattedCookie("XSRF-TOKEN"),
                    cookieContext.getCookie("XSRF-TOKEN"),
                    username,
                    password,
                    passwordSubmit
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
