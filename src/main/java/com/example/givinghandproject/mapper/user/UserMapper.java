package com.example.givinghandproject.mapper.user;

import com.example.givinghandproject.dto.User.UserRegisterRequest;
import com.example.givinghandproject.dto.User.UserResponse;
import com.example.givinghandproject.dto.User.UserUpdateRequest;
import com.example.givinghandproject.entity.User;

import java.util.ArrayList;

public class UserMapper {

    public static User toUser(UserRegisterRequest dto)
    {
        if(dto == null) return null;
        User user = new User();

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBirthDate(dto.getBirthDate());
        user.setRole(dto.getRole());
        user.setBio(dto.getBio());
        return user;
    }

    public static UserResponse fromUser(User user)
    {
        if(user == null ) return null ;
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setBio(user.getBio());
        response.setBirthDate(user.getBirthDate());
        //Donation history adding
        response.setHistory(new ArrayList<>(user.getDonationLogHistory()));
        return response;
    }

    public static void updateUserFromDto(User user , UserUpdateRequest request)
    {
        if(user == null || request == null) return;

        if(request.getName()!=null && !request.getName().isBlank()) { // exist and not empty
            user.setFullName(request.getName());

        }
        // you handle the (Name) blank validation in Dto Constraint validation already
        if(request.getBio()!=null){ // just exist enough maybe want to remove it
            user.setBio(request.getBio());
        }
    }


}
