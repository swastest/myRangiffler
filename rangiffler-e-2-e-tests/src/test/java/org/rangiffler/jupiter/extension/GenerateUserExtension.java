package org.rangiffler.jupiter.extension;

import io.qameta.allure.AllureId;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.extension.*;
import org.rangiffler.api.rest.AuthRestClient;
import org.rangiffler.api.rest.UserDataRestClient;
import org.rangiffler.db.dao.*;
import org.rangiffler.db.entity.auth.UserAuthEntity;
import org.rangiffler.db.entity.userdata.UserDataEntity;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.DataUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.rangiffler.utils.DataUtils.addImageByClassPath;

public class GenerateUserExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {
    public static ExtensionContext.Namespace CREATE_USER_API = ExtensionContext.Namespace.create(GenerateUserExtension.class);
    private final AuthRestClient authRestClient = new AuthRestClient();
    private final UserDataRestClient userDataRestClient = new UserDataRestClient();
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

    private void addAvatarIfPresent(GenerateUser annotation, UserJson userJson) {

        if (annotation.avatarPath().length() != 0) {
            userJson.setAvatar(addImageByClassPath(annotation.avatarPath()));
            userDataRestClient.updateUser(userJson);
        }
    }

    private void incomeInvitationsIfPresent(Friend[] incomeInvitations, UserJson targetUser) {
        if (isNotEmpty(incomeInvitations)) {
            for (Friend friend : incomeInvitations) {
                UserJson friendJson = createRandomUser();
                userDataRestClient.sendInviteFriend(friendJson.getUsername(), targetUser.getUsername());
                targetUser.getIncomeInvitations().add(friendJson);
            }
        }
    }

    private void addFriendsIfPresent(Friend[] friends, UserJson targetUser) {
        if (isNotEmpty(friends)) {
            for (Friend friend : friends) {
                UserJson friendJson = createRandomUser();
                userDataRestClient.sendInviteFriend(targetUser.getUsername(), friendJson.getUsername());
                userDataRestClient.acceptInvitation(friendJson.getUsername(), targetUser.getUsername());
                targetUser.getFriends().add(friendJson);
            }
        }
    }

    private void outcomeInvitationsIfPresent(Friend[] outcomeInvitations, UserJson targetUser) {
        if (isNotEmpty(outcomeInvitations)) {
            for (Friend friend : outcomeInvitations) {
                UserJson friendJson = createRandomUser();
                userDataRestClient.sendInviteFriend(targetUser.getUsername(), friendJson.getUsername());
                targetUser.getOutcomeInvitations().add(friendJson);
            }
        }
    }

    private UserJson createRandomUser() {
        UserJson userJson;
        final String username = DataUtils.generateRandomUsername();
        final String password = DataUtils.generateRandomPassword();
        userJson = doRegistration(username, password, password);

        return userJson;
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
        UserJson userJson = context.getStore(CREATE_USER_API).get(getTestId(context), UserJson.class);
        if (userJson != null) {
            UserAuthDao authDao = new UserAuthDaoJdbcImpl();
            UserAuthEntity user = authDao.userInfo(userJson.getUsername());
            authDao.deleteUser(user);
            UserDataDao userdataDao = new UserDataDaoJdbcImpl();
            UserDataEntity userdata = userdataDao.userInfoByUserName(userJson.getUsername());
            userdataDao.deleteUser(userdata);
            if (!userJson.getFriends().isEmpty() || !userJson.getIncomeInvitations().isEmpty()
                    || !userJson.getOutcomeInvitations().isEmpty()) {
                List<UserJson> friends = new ArrayList<>();
                friends.addAll(userJson.getFriends());
                friends.addAll(userJson.getIncomeInvitations());
                friends.addAll(userJson.getOutcomeInvitations());
                for (UserJson friend : friends) {
                    UserAuthEntity userFriend = authDao.userInfo(friend.getUsername());
                    authDao.deleteUser(userFriend);
                    UserDataEntity userdataFriend = userdataDao.userInfoByUserName(friend.getUsername());
                    userdataDao.deleteUser(userdataFriend);
                }
            }

            if (!userJson.getPhotos().isEmpty()) {
                PhotoDao photoDao = new PhotoDaoJdbcImpl();
                List<PhotoJson> photoJsons = userJson.getPhotos();
                for (PhotoJson photo : photoJsons) {
                    photoDao.deleteAllPhotoByUsername(photo.getUsername());
                }
            }

        }
    }

    @Step("Create new user")
    private UserJson doRegistration(String username, String password, String submitPassword) {
        authRestClient.getRegistrationToken();
        authRestClient.doRegistration(username, password, submitPassword);
        try {
            Awaitility.await()
                    .atMost(Duration.ofSeconds(10)) // Максимальное время ожидания (10 секунд в данном случае)
                    .pollDelay(Duration.ofMillis(100)) // Интервал между проверками (100 миллисекунд в данном случае)
                    .until(() -> userDataRestClient.getCurrentUserInfo(username) != null);
        } catch (ConditionTimeoutException e) {
            throw new RuntimeException(e.getMessage());
        }
        UserJson currentUserInfo = userDataRestClient.getCurrentUserInfo(username);
        currentUserInfo.setPassword(password);
        return currentUserInfo;
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }
}
