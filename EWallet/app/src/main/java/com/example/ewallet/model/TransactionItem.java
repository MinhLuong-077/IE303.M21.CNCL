package com.example.ewallet.model;

public class TransactionItem {
    // declaration variable
    private String message, datetime, money;

    // constructor
    public TransactionItem(String message, String datetime, String money)
    {
        this.message = message;
        this.datetime = datetime;
        this.money = money;
    }

    // getter and setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
