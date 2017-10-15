package pes.agorapp.network;

import com.google.gson.JsonObject;

import pes.agorapp.JSONObjects.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marc on 15/10/17.
 */

public interface AgorAppApiService {

    @POST("users?")
    Call<User> createUser(
            @Body JsonObject user);

}
