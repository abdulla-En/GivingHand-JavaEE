package com.example.givinghandproject.service;

import com.example.givinghandproject.dao.UserRepo;
import com.example.givinghandproject.dto.User.UserLoginRequest;
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

    public String login (UserLoginRequest request)
    {
        // don't forget here we just do a Business-validation

        User user = repository.getByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("email", "This email is not registered"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BusinessException("password", "Invalid password, please try again");
        }

        return user.getFullName().trim().split(" ")[0];

    }

    // not secure version before security phase
    public Long update (Long id ,UserUpdateRequest request)
    {
        // get user
        User user = repository.getById(id)
                .orElseThrow(()->new BusinessException("user" , "there is o user with id : "+id));
        // Mapper update
        UserMapper.updateUserFromDto(user,request);

        // ensuring
        repository.update(user);

        return user.getId();
    }
}

// getemail() -> return optional<user> singleResult or empty
// ifPresent(task) -> check if there is a result   [register used]
// orElseThrow(task) -> check if empty             [Login used]