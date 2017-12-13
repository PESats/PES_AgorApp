package pes.agorapp.JSONObjects;

/**
 * Created by Alex on 01-Dec-17.
 */

public class Bid {
    private int ammount;
    private boolean accepted;
    private UserAgorApp user;
    private Announcement announcement;
    private int id;

    public Bid(int ammount, boolean accepted, UserAgorApp user, Announcement announcement, int id) {
        this.ammount = ammount;
        this.accepted = accepted;
        this.user = user;
        this.announcement = announcement;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReward() {
        return ammount;
    }

    public void setReward(int reward) {
        this.ammount = ammount;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public UserAgorApp getUser() {
        return user;
    }

    public void setUser(UserAgorApp user) {
        this.user = user;
    }

}
