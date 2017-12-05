package pes.agorapp.network;

import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Botiga;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.UserAgorApp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marc on 15/10/17.
 */

public interface AgorAppApiService {

    /* USUARI */

    @POST("users/login")
    Call<UserAgorApp> createUser(
            @Body JsonObject user
    );

    @POST("users/logout")
    Call<UserAgorApp> logoutUser(
            @Body JsonObject user
    );

    @PUT("users")
    Call<UserAgorApp> upgradeBotiguer(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    /* ANUNCIS */

    @GET("anuncis")
    Call<ArrayList<Announcement>> getAnnouncements(
            @Query("user_id") int id,
            @Query("active_token") String token
    );

    @GET("anuncis/{id}")
    Call<Announcement> getAnnouncement(
            @Path("user_id") int id,
            @Path("active_token") String token
    );

    @POST("anuncis")
    Call<Announcement> createAnnouncement(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token,
            @Body JsonObject announcement
    );

    @POST("anuncis/{id}/comentaris?")
    Call<Comment> createAnnouncementComment(
            @Path("id") int id,
            @Query("user_id") String user_id,
            @Query("active_token") String active_token,
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
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    /* COMENTARIS */

    @GET("anuncis/{announcementId}/comentaris")
    Call<ArrayList<Comment>> getComments(
            @Path("announcementId") int id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
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

    /* BOTIGUES */

    @POST("shops")
    Call<Botiga> createShop(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token,
            @Body JsonObject shop
    );

    /* CUPONS DE DESCOMPTE */

    /*
    @POST("coupons")
    Call<ValDescompte> createValDescompte(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token,
            @Body JsonObject valDescompte
    );
    */

}
