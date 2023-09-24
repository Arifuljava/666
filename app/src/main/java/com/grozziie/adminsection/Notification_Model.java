package com.grozziie.adminsection;


public class Notification_Model {
    String username,feedback,time;

    public Notification_Model() {
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

    public Notification_Model(String username, String feedback, String time) {
        this.username = username;
        this.feedback = feedback;
        this.time = time;
    }
}