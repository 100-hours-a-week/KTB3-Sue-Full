package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserNicknameRequest;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Qualifier("updateUserNickname")
public class UpdateUserNicknameImpl implements UpdateUserService<User, UpdateUserNicknameRequest> {
    private final UserRepository databaseUserRepository;

    public UpdateUserNicknameImpl(UserRepository databaseUserRepository){
        this.databaseUserRepository = databaseUserRepository;
    }

    @Override
    public User update(Long user_id, UpdateUserNicknameRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setNickname(req.getNickname());
        return user;
    }
}
