package pes.agorapp.JSONObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 01-Nov-17.
 */

public class Announcement {
    private String title;
    private String text;
    private Location location;
    private int rewardPoints;
    private Date date;


    public Announcement(String title, String text, Location location, int rewardPoints, Date date) {
        this.title = title;
        this.text = text;
        this.location = location;
        this.rewardPoints = rewardPoints;
        this.date = date;
    }

    @Override
    public String toString() {
        JSONObject announcement = new JSONObject();
        try {
            announcement.put("title", title);
            announcement.put("text", text);
            announcement.put("location", location.toString());
            announcement.put("rewardPoints", rewardPoints);
            announcement.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return announcement.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
