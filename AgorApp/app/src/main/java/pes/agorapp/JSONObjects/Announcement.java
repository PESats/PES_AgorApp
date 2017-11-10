package pes.agorapp.JSONObjects;

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
    private Date created_at;


    public Announcement() {}
    public Announcement(String title, String text, float latitude, float longitude, int reward,
                        int user_id, Date created_at) {
        this.title = title;
        this.description = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reward = reward;
        this.user_id = user_id;
        this.created_at = created_at;
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
}
