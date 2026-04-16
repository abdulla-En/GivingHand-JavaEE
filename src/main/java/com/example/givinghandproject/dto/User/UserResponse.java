package com.example.givinghandproject.dto.User;

import com.example.givinghandproject.utilities.enums.UserType;

import java.time.LocalDate;

public class UserResponse {
    private String fullName;

    private String email;

    private Long id;

    private LocalDate birthDate;

    private String bio;

    private UserType role;

    // JPA have to use a default constructor
    public UserResponse(){}

    // Getters and Setters to JSON Ser&Des


    public String getFullName(){return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public UserType getRole() { return role; }
    public void setRole(UserType role) { this.role = role; }

}
