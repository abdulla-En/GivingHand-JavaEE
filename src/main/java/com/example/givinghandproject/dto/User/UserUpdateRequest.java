package com.example.givinghandproject.dto.User;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequest {

    @NotBlank(message = "name cannot be empty")
    private String name ;
    private String bio;


    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }
}
