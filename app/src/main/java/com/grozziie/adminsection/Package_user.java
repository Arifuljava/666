package com.grozziie.adminsection;

public class Package_user {
    String username,package_name,date,status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Package_user(String username, String package_name, String date, String status) {
        this.username = username;
        this.package_name = package_name;
        this.date = date;
        this.status = status;
    }

    public Package_user() {
    }
}