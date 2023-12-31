package org.rangiffler.jupiter.extension;

import guru.qa.grpc.rangiffler.grpc.Country;
import guru.qa.grpc.rangiffler.grpc.CountryByCodeRequest;
import guru.qa.grpc.rangiffler.grpc.Photos;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
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

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.rangiffler.utils.DataUtils.convertImage;

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

    @Step("Add photo for user {1}")
    private void addPhotoIfPresent(Photo[] photos, UserJson targetUser) {
        if (isNotEmpty(photos)) {
            Photos.Builder builder = Photos.newBuilder();
            for (Photo photo : photos) {
                Country country = countryGrpcClient.getCountryByCode(photo.countryCode());
                guru.qa.grpc.rangiffler.grpc.Photo reqOnePhoto = guru.qa.grpc.rangiffler.grpc.Photo.newBuilder()
                        .setUsername(targetUser.getUsername())
                        .setPhoto(convertImage(photo.photoPath()))
                        .setDescription(photo.description())
                        .setCountry(country)
                        .build();
                builder.addPhotos(reqOnePhoto);
                guru.qa.grpc.rangiffler.grpc.Photo resp = photoGrpcClient.addPhoto(reqOnePhoto);
                targetUser.getPhotos().add(PhotoJson.convertFromGrpc(resp));
            }
        }
    }

    @Step("Add avatar for user {1}")
    private void addAvatarIfPresent(GenerateUser annotation, UserJson userJson) {

        if (annotation.avatarPath().length() != 0) {
            userJson.setAvatar(convertImage(annotation.avatarPath()));
            userDataRestClient.updateUser(userJson);
        }
    }

    @Step("Income friend invitation ")
    private void incomeInvitationsIfPresent(Friend[] incomeInvitations, UserJson targetUser) {
        if (isNotEmpty(incomeInvitations)) {
            for (Friend friend : incomeInvitations) {
                UserJson friendJson = createRandomUser();
                userDataRestClient.sendInviteFriend(friendJson.getUsername(), targetUser.getUsername());
                addPhotoIfPresent(friend.photos(), friendJson);
                targetUser.getIncomeInvitations().add(friendJson);
            }
        }
    }

    @Step("Add friend")
    private void addFriendsIfPresent(Friend[] friends, UserJson targetUser) {
        if (isNotEmpty(friends)) {
            for (Friend friend : friends) {
                UserJson friendJson = createRandomUser();
                userDataRestClient.sendInviteFriend(targetUser.getUsername(), friendJson.getUsername());
                userDataRestClient.acceptInvitation(friendJson.getUsername(), targetUser.getUsername());
                addPhotoIfPresent(friend.photos(), friendJson);
                targetUser.getFriends().add(friendJson);
            }
        }
    }

    @Step("outcome friend invitation")
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

    @Step("Registration new user. username= {0}, password= {1}")
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
}
