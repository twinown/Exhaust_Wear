package com.example.exhaustwear.Model;

public class Users {
    //its important to make equal words here and keys(hash map) in database
    //or in the loginFragment use "equalsIgnoreCase" method
    private String name, phone, email, password;

    public Users() {
    }

    public Users( String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}