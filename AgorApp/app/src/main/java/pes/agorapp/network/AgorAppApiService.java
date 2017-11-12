package pes.agorapp.network;

import com.google.gson.JsonObject;

import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.UserAgorApp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("comments/{id}")
    Call<Comment> editComment(
            @Path("id") int id,
            @Body JsonObject user
    );

    @DELETE("comments/{id}")
    Call<Comment> deleteComment(
            @Path("id") int id,
            @Body JsonObject user
    );

}
