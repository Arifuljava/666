package com.grozziie.adminsection;



public class Chat {
    String username,feedback,time,uuid;

    public Chat() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Chat(String username, String feedback, String time, String uuid) {
        this.username = username;
        this.feedback = feedback;
        this.time = time;
        this.uuid = uuid;
    }
}