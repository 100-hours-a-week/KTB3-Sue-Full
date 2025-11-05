package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdatePasswordRequest;
import com.example.spring_restapi.dto.response.UserInfoResponse;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Qualifier("updateUserPassword")
public class UpdateUserPasswordImpl implements UpdateUserService<UserInfoResponse, UpdatePasswordRequest>{
    private final UserRepository databaseUserRepository;

    public UpdateUserPasswordImpl(UserRepository databaseUserRepository){
        this.databaseUserRepository = databaseUserRepository;
    }

    @Override
    @Transactional
    public UserInfoResponse update(Long user_id, UpdatePasswordRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User updateUser = findUser.get();

        if(!updateUser.getPassword().equals(req.getCurrentPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        updateUser.changePassword(req.getNewPassword(), req.getNewPasswordConfirm());

        databaseUserRepository.update(updateUser);

        return new UserInfoResponse(
                updateUser.getId(), updateUser.getEmail(), updateUser.getUserRole()
        );
    }
}
