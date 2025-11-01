package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserRequest;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Qualifier("updateUserInfo")
public class UpdateUserInfoImpl implements UpdateUserService<User, UpdateUserRequest>{
    private final UserRepository databaseUserRepository;

    public UpdateUserInfoImpl(UserRepository databaseUserRepository){
        this.databaseUserRepository = databaseUserRepository;
    }

    @Override
    public User update(Long id, UpdateUserRequest req) {
        Optional<User> findUser = databaseUserRepository.findUserById(id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        if(!findUser.get().getPassword().equals(req.getCurrentPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        User data = findUser.get();

        if(!req.getNewPassword().isEmpty()){
            data.setPassword(req.getNewPassword());
        }

        data.setNickname(req.getNickname());
        data.setProfileImage(req.getProfile_image());
        data.setIntroduce(req.getIntroduce());

        Optional<User> updateUser = databaseUserRepository.update(data);
        if(updateUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return updateUser.get();
    }
}
