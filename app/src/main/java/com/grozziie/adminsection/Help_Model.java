package com.grozziie.adminsection;



public class Help_Model {
    String name,phone,uuid,time;


    public Help_Model() {
    }

    public Help_Model(String name, String phone, String uuid, String time) {
        this.name = name;
        this.phone = phone;
        this.uuid = uuid;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}