package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdatePasswordRequest;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Qualifier("updateUserPassword")
public class UpdateUserPasswordImpl implements UpdateUserService<User, UpdatePasswordRequest>{
    private final UserRepository databaseUserRepository;

    public UpdateUserPasswordImpl(UserRepository databaseUserRepository){
        this.databaseUserRepository = databaseUserRepository;
    }

    @Override
    public User update(Long id, UpdatePasswordRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        System.out.println(user.getPassword());
        if(!findUser.get().getPassword().equals(req.getCurrentPassword())){
            System.out.println(req.getCurrentPassword());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        user.setPassword(req.getNewPassword());
        Optional<User> updateUser = databaseUserRepository.update(user);
        if(updateUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return updateUser.get();
    }

}
