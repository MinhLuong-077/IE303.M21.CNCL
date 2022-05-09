package com.example.ewallet.utils;

public class Users {
    public String full_name, email, phone;
    public Number balance;

    public Users(){

    }

    public Users(String full_name, String email, String phone, Number balance) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Number getBalance() {
        return balance;
    }

    public void setBalance(Number balance) {
        this.balance = balance;
    }
}
