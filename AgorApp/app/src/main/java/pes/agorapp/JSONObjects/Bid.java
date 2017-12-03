package pes.agorapp.JSONObjects;

/**
 * Created by Alex on 01-Dec-17.
 */

public class Bid {
    private int reward;
    private boolean accepted;
    private UserAgorApp user;
    private int announcementId;
    private int id;

    public Bid(int reward, boolean accepted, UserAgorApp user, int announcementId, int id) {
        this.reward = reward;
        this.accepted = accepted;
        this.user = user;
        this.announcementId = announcementId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
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

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }
}
