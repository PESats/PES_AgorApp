package pes.agorapp.JSONObjects;

import java.util.Date;

/**
 * Created by Alex on 24-Nov-17.
 */

public class Chat {

    private UserAgorApp user;
    private String lastMessage;
    private Date lastMessageDate;

    public Chat() { }

    public Chat(UserAgorApp user, String lastMessage, Date lastMessageDate) {
        this.user = user;
        this.lastMessage = lastMessage;
        this.lastMessageDate = lastMessageDate;
    }

    public UserAgorApp getUser() {
        return user;
    }

    public void setUser(UserAgorApp user) {
        this.user = user;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

}
