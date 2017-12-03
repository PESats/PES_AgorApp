package pes.agorapp.JSONObjects;

import java.util.Date;

/**
 * Created by Alex on 24-Nov-17.
 */

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private int bidId;

    public ChatMessage(String messageText, String messageUser, int bidId) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.bidId = bidId;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage() {

    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}