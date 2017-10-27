package pes.agorapp.network;

import com.google.gson.JsonObject;

import pes.agorapp.JSONObjects.UserAgorApp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by marc on 15/10/17.
 */

public interface AgorAppApiService {

    @POST("users/login")
    Call<UserAgorApp> createUser(
            @Body JsonObject user);

    @POST("users/logout")
    Call<UserAgorApp> logoutUser(
            @Body JsonObject user);

}
