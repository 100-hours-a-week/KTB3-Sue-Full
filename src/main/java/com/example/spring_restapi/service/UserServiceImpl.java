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
public class UserServiceImpl implements UserService {
    private final UserRepository databaseUserRepository;

    private final UpdateUserService<User, UpdateUserRequest> updateUserInfo;
    private final UpdateUserService<User, UpdatePasswordRequest> updateUserPassword;

    public UserServiceImpl(
            UserRepository databaseUserRepository,
            UpdateUserService<User, UpdateUserRequest> updateUserInfo,
            UpdateUserService<User, UpdatePasswordRequest> updateUserPassword
    ){
        this.databaseUserRepository = databaseUserRepository;
        this.updateUserInfo = updateUserInfo;
        this.updateUserPassword = updateUserPassword;
    }

    @Override
    public User login(LoginRequest req){
        if(req.getEmail().isEmpty() || req.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(req.getEmail());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }

        User loginUser = findUser.get();

        // 비밀번호 오류
        if(!loginUser.getPassword().equals(req.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid_credentials");
        }

        return loginUser;
    }

    @Override
    public SignUpResponse signup(SignUpRequest req){
        if(req.getEmail().isEmpty() || req.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(req.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already_exists");
        }

        User newUser = new User(
                null, // 자동 생성
                req.getEmail(),
                req.getPassword(),
                req.getNickname(),
                req.getProfile_image(),
                req.getIntroduce(),
                null
        );

        User data = databaseUserRepository.save(newUser);

        return new SignUpResponse(data.getUser_id(), data.getEmail(), data.getNickname(), data.getProfileImage(), data.getIntroduce());
    }

    @Override
    public User updateUser(Long user_id, UpdateUserRequest req){
        return updateUserInfo.update(user_id, req);
    }

    @Override
    public User removeUser(Long user_id){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);
        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return databaseUserRepository.deleteUserById(findUser.get().getUser_id());
    }

    @Override
    public List<User> getAllUsers(){
        return databaseUserRepository.findAllUser();
    }

    @Override
    public User getUserById(Long user_id){
        return databaseUserRepository.findUserById(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
    }

    @Override
    public User getUserByEmail(String email){
        return databaseUserRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
    }

    @Override
    public User updateUserPassword(Long user_id, UpdatePasswordRequest req){
        return updateUserPassword.update(user_id, req);
    }

    @Override
    public User updateUserNickname(Long user_id, UpdateUserNicknameRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setNickname(req.getNickname());
        return user;
    }

    @Override
    public User updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setProfileImage(req.getProfile_image());
        return user;
    }

    @Override
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
