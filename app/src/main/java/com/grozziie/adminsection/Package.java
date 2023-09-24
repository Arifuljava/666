package com.grozziie.adminsection;

public class Package {
    String useremail,package_name,package_price,payment_methode,usernumber,transacation,uuid,user_uuid,
            status,username,date,package_type,time;

    public Package() {
    }

    public Package(String useremail, String package_name,
                   String package_price, String payment_methode, String usernumber,
                   String transacation, String uuid,
                   String user_uuid, String status, String username, String date,
                   String package_type, String time) {
        this.useremail = useremail;
        this.package_name = package_name;
        this.package_price = package_price;
        this.payment_methode = payment_methode;
        this.usernumber = usernumber;
        this.transacation = transacation;
        this.uuid = uuid;
        this.user_uuid = user_uuid;
        this.status = status;
        this.username = username;
        this.date = date;
        this.package_type = package_type;
        this.time = time;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getPayment_methode() {
        return payment_methode;
    }

    public void setPayment_methode(String payment_methode) {
        this.payment_methode = payment_methode;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getTransacation() {
        return transacation;
    }

    public void setTransacation(String transacation) {
        this.transacation = transacation;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}