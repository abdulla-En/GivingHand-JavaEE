package com.example.givinghandproject.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {

    @Email(message = "Wrong Email format ")
    @NotBlank(message = "Email is Required")
    private String email ;

    @NotBlank(message = "Password Required")
    private String password ;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
