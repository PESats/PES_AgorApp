package pes.agorapp.network;

import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.JSONObjects.Botiga;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.JSONObjects.Trophy;
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

    @GET("users/{id}")
    Call<UserAgorApp> getUser(
            @Path("id") int id,
            @Query("user_id") int idUser,
            @Query("active_token") String token
    );

    @POST("users/login")
    Call<UserAgorApp> createUser(
            @Body JsonObject user
    );

    @POST("users/logout")
    Call<UserAgorApp> logoutUser(
            @Body JsonObject user
    );

    /* ANUNCIS */

    @GET("anuncis")
    Call<ArrayList<Announcement>> getAnnouncements(
            @Query("user_id") int id,
            @Query("active_token") String token
    );

    @GET("anuncis/{id}")
    Call<Announcement> getAnnouncement(
            @Path("id") int idAnunci,
            @Query("user_id") int idUser,
            @Query("active_token") String token
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

    /* BIDS */

    @GET("anuncis/{id}/bids")
    Call<ArrayList<Bid>> getBidsAnnouncement(
            @Path("id") int idAnunci,
            @Query("user_id") int idUser,
            @Query("active_token") String token
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

    @GET("shops")
    Call<ArrayList<Botiga>> getShops(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @GET("shops/{id}")
    Call<Botiga> getShop(
            @Path("id") int id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @POST("shops")
    Call<Botiga> createShop(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token,
            @Body JsonObject shop
    );

    /* CUPONS DE DESCOMPTE */

    @GET("coupons")
    Call<ArrayList<Coupon>> getCoupons(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @GET("coupon/{id}")
    Call<Coupon> getCoupon(
            @Path("id") int id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @POST("coupons")
    Call<Coupon> createCoupon(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token,
            @Body JsonObject coupon
    );

    @DELETE("coupons/{id}")
    Call<Coupon> deleteCoupon(
            @Path("id") int id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @GET("users/{id}/bought_coupons")
    Call<ArrayList<Coupon>> getBoughtCoupons(
            @Path("id") int id,
            @Query("active_token") String active_token
    );

    @GET("users/{id}/bought_coupons/{couponId}")
    Call<Coupon> getBoughtCoupon(
            @Path("id") int id,
            @Path("couponId") int coupon_id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @POST("users/{id}/bought_coupons")
    Call<UserAgorApp> buyCoupon(
            @Path("id") int id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token,
            @Query("coupon_id") Integer coupon_id
    );

    @DELETE("users/{id}/bought_coupons/{couponId}")
    Call<Coupon> spendCoupon(
            @Path("id") int id,
            @Path("couponId") int coupon_id,
            @Query("active_token") String active_token
    );

    /* LICITACIONS (BIDS) */

    @GET("bids")
    Call<ArrayList<Bid>> getBids(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @GET("users/{user_id}/bids")
    Call<ArrayList<Bid>> getBidsWithFilters(
            @Path("user_id") int user_id,
            @Query("active_token") String active_token,
            @Query("filter_mode") String filter_mode
    );

    /* TROFEUS */
    @GET("badges")
    Call<ArrayList<Trophy>> getTrophies(
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );

    @GET("users/{user_id}/badges")
    Call<ArrayList<Trophy>> getUserTrophies(
            @Path("user_id") int id,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
    );
    @POST("/users/{id}/bids")
    Call<Bid> newBid(
            @Path("id") int id,
            @Body JsonObject bid
    );

    @PUT("/users/{userId}/anuncis/{anunciId}/select")
    Call<Bid> acceptBid(
            @Path("userId") int userId,
            @Path("anunciId") int anunciId,
            @Body JsonObject json
    );

    @PUT("/users/{userId}/anuncis/{anunciId}/complete")
    Call<Bid> payBid(
            @Path("userId") int userId,
            @Path("anunciId") int anunciId,
            @Body JsonObject json
    );


}
