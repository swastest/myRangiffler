package org.rangiffler.api;


import org.rangiffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserDataService {
    @GET("/currentUser")
    Call<UserJson> currentUser (
            @Query("username") String username
    );
}
