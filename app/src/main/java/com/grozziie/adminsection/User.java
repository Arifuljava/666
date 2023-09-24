package com.grozziie.adminsection;



public class User {
    String username,email, ammount,status,date,time,paymentmethode,paymentnumber;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaymentmethode() {
        return paymentmethode;
    }

    public void setPaymentmethode(String paymentmethode) {
        this.paymentmethode = paymentmethode;
    }

    public String getPaymentnumber() {
        return paymentnumber;
    }

    public void setPaymentnumber(String paymentnumber) {
        this.paymentnumber = paymentnumber;
    }

    public User(String username, String email,
                String ammount, String status, String date, String time, String paymentmethode, String paymentnumber) {
        this.username = username;
        this.email = email;
        this.ammount = ammount;
        this.status = status;
        this.date = date;
        this.time = time;
        this.paymentmethode = paymentmethode;
        this.paymentnumber = paymentnumber;
    }
}