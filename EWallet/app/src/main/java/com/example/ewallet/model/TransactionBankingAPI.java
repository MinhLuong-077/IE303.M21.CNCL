package com.example.ewallet.model;

import java.util.Date;

public class TransactionBankingAPI {
    // UserId string `gorm:"primaryKey;not null"`
    // ID string `gorm:"primaryKey;type:varchar(50)"`
    // Date time.Time `gorm:"type:datetime"`
    // Name string `gorm:"type:varchar(50)"`
    // Account string `gorm:"type:varchar(50)"`
    // Bank string `gorm:"type:varchar(20)"`
    // Money float64 `gorm:"type:float"`
    // Status string `gorm:"type:varchar(15)"`
    // Message string `gorm:"type:varchar(255)"`
    private String UserId;
    private String ID;
    private Date Date;
    private String Name;
    private String Account;
    private String Bank;
    private float Money;
    private String Status;
    private String Message;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public float getMoney() {
        return Money;
    }

    public void setMoney(float money) {
        Money = money;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
