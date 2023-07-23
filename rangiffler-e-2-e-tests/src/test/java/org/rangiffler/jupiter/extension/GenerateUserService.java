package org.rangiffler.jupiter.extension;

import guru.qa.grpc.rangiffler.grpc.Country;
import guru.qa.grpc.rangiffler.grpc.CountryByCodeRequest;
import guru.qa.grpc.rangiffler.grpc.Photos;
import io.qameta.allure.AllureId;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rangiffler.api.grpc.CountryGrpcClient;
import org.rangiffler.api.grpc.PhotoGrpcClient;
import org.rangiffler.api.rest.AuthRestClient;
import org.rangiffler.api.rest.UserDataRestClient;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.annotation.Photo;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.DataUtils;

import javax.annotation.Nonnull;

import java.time.Duration;
import java.util.Objects;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.rangiffler.utils.DataUtils.addImageByClassPath;

public class GenerateUserService {
    private final AuthRestClient authRestClient = new AuthRestClient();
    private final UserDataRestClient userDataRestClient = new UserDataRestClient();
    private final CountryGrpcClient countryGrpcClient = new CountryGrpcClient();
    private final PhotoGrpcClient photoGrpcClient = new PhotoGrpcClient();

    public UserJson generateUser(@Nonnull GenerateUser annotation) {
        UserJson userJson;
        if (annotation.username().length() >= 3) {
            userJson = doRegistration(annotation.username(), annotation.password(), annotation.password());
        } else if (annotation.username().length() == 0) {
            userJson = createRandomUser();
        } else throw new IllegalArgumentException("### Username length < 3: " + annotation.username());
        addAvatarIfPresent(annotation, userJson);
        addFriendsIfPresent(annotation.friends(), userJson);
        outcomeInvitationsIfPresent(annotation.outcomeInvitations(), userJson);
        incomeInvitationsIfPresent(annotation.incomeInvitations(), userJson);
        addPhotoIfPresent(annotation.photos(), userJson);
        return userJson;
    }

    private void addPhotoIfPresent(Photo[] photos, UserJson targetUser) {
        if(isNotEmpty(photos)) {
            Photos.Builder builder = Photos.newBuilder();
            for(Photo photo: photos) {
                CountryByCodeRequest req = CountryByCodeRequest.newBuilder()
                        .setCode(photo.countryCode())
                        .build();
                Country country = countryGrpcClient.getCountryByCode(req);
                guru.qa.grpc.rangiffler.grpc.Photo reqOnePhoto = guru.qa.grpc.rangiffler.grpc.Photo.newBuilder()
                        .setUsername(targetUser.getUsername())
                        .setPhoto(addImageByClassPath(photo.photoPath()))
                        .setDescription(photo.description())
                        .setCountry(country)
                        .build();
                builder.addPhotos(reqOnePhoto);
                guru.qa.grpc.rangiffler.grpc.Photo resp = photoGrpcClient.addPhoto(reqOnePhoto);
                targetUser.getPhotos().add(PhotoJson.convertFromGrpc(resp));
            }
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
