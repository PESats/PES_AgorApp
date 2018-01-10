package pes.agorapp.JSONObjects;

import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 01-Nov-17.
 */

public class Announcement {
    private int id;
    private String title;
    private String description;
    private float latitude;
    private float longitude;
    private int reward;
    private int user_id;
    private String status;
    private Date created_at;
    private UserAgorApp user;

    private UserAgorApp userAgorApp;


    public Announcement() {}

    public Announcement(String title, String text, float latitude, float longitude, int reward,
                        int user_id, Date created_at, UserAgorApp userAgorApp, String status) {
        this.title = title;
        this.description = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reward = reward;
        this.user_id = user_id;
        this.created_at = created_at;
        this.userAgorApp = userAgorApp;
        this.status = status;
    }

    @Override
    public String toString() {
        JSONObject announcement = new JSONObject();
        try {
            announcement.put("title", title);
            announcement.put("text", description);
            announcement.put("latitude", latitude);
            announcement.put("longitude", longitude);
            announcement.put("reward", reward);
            announcement.put("user_id", user_id);
            announcement.put("created_at", created_at);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return announcement.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public UserAgorApp getUser() {
        return user;
    }

    public void setUser(UserAgorApp user) {
        this.user = user;
    }

}
