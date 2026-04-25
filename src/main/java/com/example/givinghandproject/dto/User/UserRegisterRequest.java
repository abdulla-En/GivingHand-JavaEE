package com.example.givinghandproject.dto.User;

import com.example.givinghandproject.utilities.enums.UserType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserRegisterRequest {

    @NotBlank(message = "Name is Required")
    private String fullName;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Password is Required")
    // create a pass regex
    @Size(min = 8, max = 20, message = "Length must be 8-20")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$" /* copy from internet */,
            message = "Password must contain at least one digit, one uppercase, one lowercase, one special character, and no whitespace"
    )
    private String password;

    @NotNull(message = "Birth Date Required")
    private LocalDate birthDate;

    private String bio;

    @NotNull(message = "Role Required")
    private UserType role;


    // Getters and Setters to JSON Ser&Des


    public String getFullName(){return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public UserType getRole() { return role; }
    public void setRole(UserType role) { this.role = role; }
}
