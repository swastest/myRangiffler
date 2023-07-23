package org.rangiffler.api.rest;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.*;

public interface AuthService {
    @GET("/oauth2/authorize")
    Call<Void> authorized(
            @Query("response_type") String responseTypeCode,
            @Query("client_id") String clientId,
            @Query("scope") String scope,
            @Query(value = "redirect_uri", encoded = true) String redirectUri,
            @Query("code_challenge") String codeChallenge,
            @Query("code_challenge_method") String codeChallengeMethod
    );

    @POST("/login")
    @FormUrlEncoded
    Call<Void> login(
            @Header("Cookie") String jsessionId,
            @Header("Cookie") String xsrfToken,
            @Field("_csrf") String csrf,
            @Field("username") String username,
            @Field("password") String password
            );

    @POST("/oauth2/token")
    Call<JsonNode> token(
            @Header("Authorization") String basicAuthToken,
            @Query("client_id") String clientId,
            @Query(value = "redirect_uri", encoded = true) String redirectUri,
            @Query("grant_type") String grantType,
            @Query("code") String code,
            @Query("code_verifier") String codeVerifier
    );

    @GET("/register")
    Call<Void> getRegistrationToken();

    @POST("/register")
    @FormUrlEncoded
    Call<Void> doRegistration(
            @Header("Cookie") String fullXsrfToken,
            @Field("_csrf") String xsrfTokenValue,
            @Field("username") String username,
            @Field("password") String password,
            @Field("passwordSubmit") String passwordSubmit
    );
}
