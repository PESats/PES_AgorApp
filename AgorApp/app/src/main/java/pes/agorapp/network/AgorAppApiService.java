package pes.agorapp.network;

import com.google.gson.JsonObject;

import pes.agorapp.JSONObjects.UserAgorApp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by marc on 15/10/17.
 */

public interface AgorAppApiService {

    @POST("users?")
    Call<UserAgorApp> createUser(
            @Body JsonObject user);

    @POST("users/logout")
    Call<UserAgorApp> logoutUser(
            @Body JsonObject user);

}
