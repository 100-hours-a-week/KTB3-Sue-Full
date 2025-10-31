package com.example.spring_restapi.service;

import com.example.spring_restapi.model.User;
import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.dto.response.SignUpResponse;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository databaseUserRepository;

    private final UpdateUserService<User, UpdateUserRequest> updateUserInfo;
    private final UpdateUserService<User, UpdatePasswordRequest> updateUserPassword;

    public UserService(
            UserRepository databaseUserRepository,
            UpdateUserService<User, UpdateUserRequest> updateUserInfo,
            UpdateUserService<User, UpdatePasswordRequest> updateUserPassword
    ){
        this.databaseUserRepository = databaseUserRepository;
        this.updateUserInfo = updateUserInfo;
        this.updateUserPassword = updateUserPassword;
    }

    public User login(LoginRequest user){
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(user.getEmail());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }

        User loginUser = findUser.get();

        // 비밀번호 오류
        if(!loginUser.getPassword().equals(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid_credentials");
        }

        return loginUser;
    }

    public SignUpResponse signup(SignUpRequest user){
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(user.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already_exists");
        }

        User newUser = new User(
                null, // 자동 생성
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getProfile_image(),
                user.getIntroduce(),
                null
        );

        User data = databaseUserRepository.save(newUser);

        return new SignUpResponse(data.getUser_id(), data.getEmail(), data.getNickname(), data.getProfileImage(), data.getIntroduce());
    }

    public User updateUser(Long user_id, UpdateUserRequest user){
        return updateUserInfo.update(user_id, user);
    }

    public User removeUser(Long user_id){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);
        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return databaseUserRepository.deleteUserById(findUser.get().getUser_id());
    }

    public List<User> getAllUsers(){
        return databaseUserRepository.findAllUser();
    }

    public User getUserById(Long user_id){
        return databaseUserRepository.findUserById(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
    }

    public User getUserByEmail(String email){
        return databaseUserRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
    }

    public User updateUserPassword(Long user_id, UpdatePasswordRequest req){
        return updateUserPassword.update(user_id, req);
    }

    public User updateUserNickname(Long user_id, UpdateUserNicknameRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setNickname(req.getNickname());
        return user;
    }

    public User updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setProfileImage(req.getProfile_image());
        return user;
    }

    public User updateUserIntroduce(Long user_id, UpdateUserIntroduceRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setIntroduce(req.getIntroduce());
        return user;
    }
}
