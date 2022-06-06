package com.example.ewallet.model;

import java.util.Date;

public class History {
//    my is month and year
    private String my, message, money;
    private Date datetime;

    public History(String my, String message, Date datetime, String money) {
        this.my = my;
        this.message = message;
        this.datetime = datetime;
        this.money = money;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMy() {
        return my;
    }

    public void setMy(String my) {
        this.my = my;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
