package org.rangiffler.jupiter.extension;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.*;
import org.rangiffler.api.AuthRestClient;
import org.rangiffler.api.UserDataRestClient;
import org.rangiffler.db.dao.UserAuthDao;
import org.rangiffler.db.dao.UserAuthDaoJdbcImpl;
import org.rangiffler.db.dao.UserDataDao;
import org.rangiffler.db.dao.UserDataDaoJdbcImpl;
import org.rangiffler.db.entity.auth.UserAuthEntity;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.DataUtils;

import java.util.Objects;

public class GenerateUserExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {
    public static ExtensionContext.Namespace CREATE_USER_API = ExtensionContext.Namespace.create(GenerateUserExtension.class);
    private final AuthRestClient authRestClient = new AuthRestClient();
    private final UserDataRestClient userDataRestClient = new UserDataRestClient();

    @Override
    public void beforeEach(ExtensionContext context) {
        UserJson userJson;
        GenerateUser annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateUser.class);
        if (annotation.username().length() >= 3) {
            userJson = doRegistration(annotation.username(), annotation.password(), annotation.password());
        } else if (annotation.username().length() == 0) {
            final String username = DataUtils.generateRandomUsername();
            final String password = DataUtils.generateRandomPassword();
            userJson = doRegistration(username, password, password);
        } else throw new IllegalArgumentException("### Username length < 3: " + annotation.username());
        context.getStore(CREATE_USER_API).put(getTestId(context), userJson);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(CREATE_USER_API).get(getTestId(extensionContext), UserJson.class);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        UserJson userJson = context.getStore(CREATE_USER_API).get(getTestId(context), UserJson.class);
        if (userJson != null) {
            UserAuthDao authDao = new UserAuthDaoJdbcImpl();
            UserAuthEntity user = authDao.userInfo(userJson.getUsername());
            authDao.deleteUser(user);

            UserDataDao userdataDao = new UserDataDaoJdbcImpl();
            UserDataEntity userdata = userdataDao.userInfoByUserName(userJson.getUsername());
            userdataDao.deleteUser(userdata);
        }
    }

    private UserJson doRegistration(String username, String password, String submitPassword) {
        authRestClient.getRegistrationToken();
        authRestClient.doRegistration(username, password, submitPassword);
        UserJson currentUserInfo = userDataRestClient.getCurrentUserInfo(username);
        return currentUserInfo;
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }
}
