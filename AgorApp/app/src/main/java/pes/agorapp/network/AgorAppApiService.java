package pes.agorapp.network;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import pes.agorapp.JSONObjects.Announcement;
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

    /* USUARI */

    @POST("users/login")
    Call<UserAgorApp> createUser(
            @Body JsonObject user);

    @POST("users/logout")
    Call<UserAgorApp> logoutUser(
            @Body JsonObject user);

    /* ANUNCIS */

    @GET("anuncis")
    Call<ArrayList<Announcement>> getAnnouncements(
            @Path("user_id") int id,
            @Path("active_token") String token
    );

    @GET("anuncis/{id}")
    Call<Announcement> getAnnouncement(
            @Path("user_id") int id,
            @Path("active_token") String token
    );

    @POST("anuncis")
    Call<Announcement> createAnnouncement(
            @Body JsonObject announcement
    );

    @PUT("anuncis/{id}")
    Call<Comment> editAnnouncement(
            @Path("id") int id,
            @Body JsonObject user
    );

    @DELETE("anuncis/{id}")
    Call<Announcement> deleteAnnouncement(
            @Path("id") int id,
            @Body JsonObject user
    );

    /* COMENTARIS */

    @GET("comments/{announcementId}")
    Call<ArrayList<Comment>> getComments(
            @Path("announcementId") int id,
            @Body JsonObject user
    );

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
