package com.KurdDevs.KurdDevs.DTO;

import jakarta.validation.constraints.Email;

public class UserDto {

    private String username;
    @Email(message = "Invalid email format")
    private String email;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDto() {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // Constructors, getters, setters, and other properties

    // ...
}
