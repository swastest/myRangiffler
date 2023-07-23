package org.rangiffler.api.rest;


import org.rangiffler.model.FriendJson;
import org.rangiffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserDataService {
    @GET("/currentUser")
    Call<UserJson> currentUser(
            @Query("username") String username
    );

    @POST("/addFriend")
    Call<UserJson> sendInviteFriend(
            @Query("username") String username,
            @Body FriendJson friendJson
    );

    @POST("/acceptInvitation")
    Call<UserJson> acceptInvitation(
            @Query("username") String username,
            @Body FriendJson friendJson
    );

    @PATCH("/updateUserInfo")
    Call<UserJson> updateUser(
            @Body UserJson userJson
    );

}
