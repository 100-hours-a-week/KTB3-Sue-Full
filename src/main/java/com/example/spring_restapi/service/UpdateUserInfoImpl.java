package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserInfoRequest;
import com.example.spring_restapi.dto.response.UserInfoResponse;
import com.example.spring_restapi.dto.response.UserResponse;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Qualifier("updateUserInfo")
public class UpdateUserInfoImpl implements UpdateUserService<UserInfoResponse, UpdateUserInfoRequest> {
    private final UserRepository databaseUserRepository;

    public UpdateUserInfoImpl(UserRepository databaseUserRepository) {
        this.databaseUserRepository = databaseUserRepository;
    }

    @Override
    @Transactional
    public UserInfoResponse update(Long user_id, UpdateUserInfoRequest req) {
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User updateUser = findUser.get();

        if(!updateUser.getPassword().equals(req.getCurrentPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        updateUser.changeEmail(req.getEmail());
        updateUser.changePassword(req.getNewPassword(), req.getNewPasswordConfirm());
        updateUser.changeUserRole(req.getUserRole());
        updateUser.setUpdatedAt(LocalDateTime.now());

        databaseUserRepository.update(updateUser);

        return new UserInfoResponse(
                updateUser.getId(), updateUser.getEmail(), updateUser.getUserRole());
    }
}
