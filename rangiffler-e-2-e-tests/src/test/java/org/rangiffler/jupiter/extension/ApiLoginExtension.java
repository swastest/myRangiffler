package org.rangiffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rangiffler.api.context.CookieContext;
import org.rangiffler.api.context.SessionStorageContext;
import org.rangiffler.api.rest.AuthRestClient;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.OauthUtils;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.rangiffler.config.ConfigHub.configEnv;


public class ApiLoginExtension  extends BaseExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    private static final GenerateUserService generateService = new GenerateUserService();
   private final AuthRestClient authRestClient = new AuthRestClient();
    private static final String JSESSIONID = "JSESSIONID";

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ApiLogin annotation = context.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (annotation != null) {
            String username;
            String password;
            if("".equals(annotation.username())||"".equals(annotation.password())) {
                if(isEmpty(annotation.user())) {
                throw new IllegalStateException();
                } else {
                    UserJson userJson = generateService.generateUser(annotation.user()[0]);
                    username = userJson.getUsername();
                    password  = userJson.getPassword();
                    context.getStore(GenerateUserExtension.CREATE_USER_API).put(getTestId(context), userJson);
                }
            } else {
                username = annotation.username();
                password = annotation.password();
            }

            doLogin(username, password);
        }
    }

    @Step("Authorization: username= {0}, password= {1} ")
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
