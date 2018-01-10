package pes.agorapp.JSONObjects;

/**
 * Created by Alex on 01-Dec-17.
 */

public class Bid {
    private int amount;
    private boolean accepted;
    private UserAgorApp user;
    private Announcement anunci;
    private int id;
    private String owner_name;

    public Bid(int amount, boolean accepted, UserAgorApp user, Announcement anunci, int id) {
        this.amount = amount;
        this.accepted = accepted;
        this.user = user;
        this.anunci = anunci;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReward() {
        return amount;
    }

    public void setReward(int reward) {
        this.amount = amount;
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

    public Announcement getAnunci() {
        return anunci;
    }

    public void setAnunci(Announcement anunci) {
        this.anunci = anunci;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }
}
