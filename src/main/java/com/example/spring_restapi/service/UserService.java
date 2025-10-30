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


interface UpdateMethod {
    User update();
}

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User login(LoginRequest user){
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = userRepository.findUserByEmail(user.getEmail());

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

        Optional<User> findUser = userRepository.findUserByEmail(user.getEmail());

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

        User data = userRepository.save(newUser);

        return new SignUpResponse(data.getUser_id(), data.getEmail(), data.getNickname(), data.getProfileImage(), data.getIntroduce());
    }

    public User updateUser(Long user_id, UpdateUserRequest user){

        Optional<User> findUser = userRepository.findUserById(user_id);
        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        // 비번 체크도 하기
        if(!findUser.get().getPassword().equals(user.getCurrentPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        User data = findUser.get();

        if(!user.getNewPassword().isEmpty()){
            data.setPassword(user.getNewPassword());
        }

        data.setNickname(user.getNickname());
        data.setProfileImage(user.getProfile_image());
        data.setIntroduce(user.getIntroduce());

        Optional<User> updateUser = userRepository.update(data);
        if(updateUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return updateUser.get();
    }

    public User removeUser(Long user_id){
        Optional<User> findUser = userRepository.findUserById(user_id);
        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return userRepository.deleteUserById(findUser.get().getUser_id());
    }

    public List<User> getAllUsers(){
        return userRepository.findAllUser();
    }

    public User getUserById(Long user_id){
        return userRepository.findUserById(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
    }

    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
    }

    public User updateUserPassword(Long user_id, UpdatePasswordRequest req){
        Optional<User> findUser = userRepository.findUserById(user_id);

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
        Optional<User> updateUser = userRepository.update(user);
        if(updateUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return updateUser.get();
    }

    public User updateUserNickname(Long user_id, UpdateUserNicknameRequest req){
        Optional<User> findUser = userRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setNickname(req.getNickname());
        return user;
    }

    public User updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req){
        Optional<User> findUser = userRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setProfileImage(req.getProfile_image());
        return user;
    }

    public User updateUserIntroduce(Long user_id, UpdateUserIntroduceRequest req){
        Optional<User> findUser = userRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        user.setIntroduce(req.getIntroduce());
        return user;
    }
}
