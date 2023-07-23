package org.rangiffler.jupiter.extension;

import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.*;
import org.rangiffler.db.dao.*;
import org.rangiffler.db.entity.auth.UserAuthEntity;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;

import java.util.ArrayList;
import java.util.List;

public class GenerateUserExtension extends BaseExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {
    public static ExtensionContext.Namespace CREATE_USER_API = ExtensionContext.Namespace.create(GenerateUserExtension.class);
    private static final GenerateUserService generateService = new GenerateUserService();

    @Override
    @Step("Create preconditions")
    public void beforeEach(ExtensionContext context) {
        GenerateUser annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateUser.class);
        if (annotation != null) {
            UserJson userJson = generateService.generateUser(annotation);
            context.getStore(CREATE_USER_API).put(getTestId(context), userJson);
        }
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
    @Step("Remove test data")
    public void afterEach(ExtensionContext context) {
        PhotoDao photoDao = new PhotoDaoJdbcImpl();
        UserDataDao userdataDao = new UserDataDaoJdbcImpl();
        UserAuthDao authDao = new UserAuthDaoJdbcImpl();
        UserJson userJson = context.getStore(CREATE_USER_API).get(getTestId(context), UserJson.class);
        List<PhotoJson> photos = new ArrayList<>();

        if (userJson != null) {
            UserAuthEntity user = authDao.userInfo(userJson.getUsername());
            authDao.deleteUser(user);
            UserDataEntity userdata = userdataDao.userInfoByUserName(userJson.getUsername());
            userdataDao.deleteUser(userdata);
            if (!userJson.getFriends().isEmpty() || !userJson.getIncomeInvitations().isEmpty()
                    || !userJson.getOutcomeInvitations().isEmpty()) {
                List<UserJson> friends = new ArrayList<>();
                friends.addAll(userJson.getFriends());
                friends.addAll(userJson.getIncomeInvitations());
                friends.addAll(userJson.getOutcomeInvitations());

                for (UserJson friend : friends) {
                    photos.addAll(friend.getPhotos());
                    UserAuthEntity userFriend = authDao.userInfo(friend.getUsername());
                    authDao.deleteUser(userFriend);
                    UserDataEntity userdataFriend = userdataDao.userInfoByUserName(friend.getUsername());
                    userdataDao.deleteUser(userdataFriend);
                }
            }
            // оптимизировать
            if (!userJson.getPhotos().isEmpty() || !photos.isEmpty()) {
                List<PhotoJson> photoJsons = userJson.getPhotos();
                photoJsons.addAll(photos);
                for (PhotoJson photo : photoJsons) {
                    photoDao.deleteAllPhotoByUsername(photo.getUsername());
                }
            }

        }
    }
}
