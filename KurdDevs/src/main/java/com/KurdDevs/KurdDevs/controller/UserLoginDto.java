package com.KurdDevs.KurdDevs.controller;

public class UserLoginDto {

    private String username;
    private String password;

    // Constructors, getters, setters, and other properties

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
// ...
}
