package com.example.givinghandproject.service;

import com.example.givinghandproject.dao.UserRepo;
import com.example.givinghandproject.dto.User.UserRegisterRequest;
import com.example.givinghandproject.dto.User.UserUpdateRequest;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.mapper.UserMapper;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

@Stateless
public class UserService {
    @Inject
    UserRepo repository;

    // don't forget here we just do a Business-validation
    public User register(UserRegisterRequest request)
    {
        // mail check
        repository.getByEmail(request.getEmail()).ifPresent( /* task to be performed*/
                u -> {
                    throw new BusinessException("email" , "email already exists");
                });

        // age check
        if (request.getBirthDate().isAfter(LocalDate.now().minusYears(18)))
            throw new BusinessException("birthdate", "you are under-age");

        // mapping
        User user = UserMapper.toUser(request);

        // encrypt password
        String hashedPw = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPw);

        repository.save(user);
        return user ;
    }

    public String login (String  email)
    {

        User user = repository.getByEmail(email)
                .orElseThrow(() -> new BusinessException("email", "This email is not registered"));

        return user.getFullName().trim().split(" ")[0];

    }

    // not secure version before security phase
    public String update (String email , UserUpdateRequest request)
    {
        // get user
        User user = repository.getByEmail(email)
                .orElseThrow(()->new BusinessException("user" , "there is o user with email : "+email));
        // Mapper update
        UserMapper.updateUserFromDto(user,request);

        // ensuring
        repository.update(user);

        return user.getFullName();
    }
}

// getemail() -> return optional<user> singleResult or empty
// ifPresent(task) -> check if there is a result   [register used]
// orElseThrow(task) -> check if empty       