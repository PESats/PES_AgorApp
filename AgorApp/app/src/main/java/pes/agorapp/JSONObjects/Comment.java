package pes.agorapp.JSONObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 05-Nov-17.
 */

public class Comment {

    private int id;

    // TODO: update String to UserAgorApp class
    private String user_id;
    private String text;
    private Date created_at;
    private int reward;
    private int anunci_id;
    private UserAgorApp user;

    public Comment(int id, String user_id, String text, Date date, int reward) {
        this.id = id;
        this.user_id = user_id;
        this.text = text;
        this.created_at = date;
        this.reward = reward;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return created_at;
    }

    public int getReward() { return reward; }

    public void setReward(int reward) { this.reward = reward; }

    public void setDate(Date date) {
        this.created_at = date;
    }

    public String getDateString() {
        DateFormat df = new SimpleDateFormat("HH:mm dd/MM");
        return df.format(created_at);
    }

    public int getAnunci_id() {
        return anunci_id;
    }

    public void setAnunci_id(int anunci_id) {
        this.anunci_id = anunci_id;
    }

    public UserAgorApp getUser() {
        return user;
    }

    public void setUser(UserAgorApp user) {
        this.user = user;
    }
}
