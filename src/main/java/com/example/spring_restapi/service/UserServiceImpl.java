package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.response.UserInfoResponse;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.dto.response.UserResponse;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository databaseUserRepository;
    private final UserProfileRepository databaseUserProfileRepository;

    private final UpdateUserService<UserInfoResponse, UpdateUserInfoRequest> updateUserInfo;
    private final UpdateUserService<UserInfoResponse, UpdatePasswordRequest> updateUserPassword;
    private final UpdateUserService<UserProfileResponse, UpdateUserNicknameRequest> updateUserNickname;
    private final UpdateUserService<UserProfileResponse, UpdateUserProfileImageRequest> updateUserProfileImage;
    private final UpdateUserService<UserProfileResponse, UpdateUserIntroduceRequest> updateUserIntroduce;
    private final UpdateUserService<UserProfileResponse, UpdateUserGenderRequest> updateUserGender;
    private final UpdateUserService<UserProfileResponse, UpdateUserProfileIsPrivateRequest> updateUserProfileIsPrivate;

    @Override
    public UserResponse login(LoginRequest req){
        if(req.getEmail().isEmpty() || req.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(req.getEmail());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }

        User loginUser = findUser.get();

        if(!loginUser.getPassword().equals(req.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid_credentials");
        }


        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(loginUser.getId());

        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }

        UserProfile loginUserProfile = findProfile.get();

        return new UserResponse(
                loginUser.getId(),
                loginUser.getEmail(),
                loginUser.getUserRole(),
                loginUserProfile.getNickname(),
                loginUserProfile.getProfileImage(),
                loginUserProfile.getIntroduce(),
                loginUserProfile.getGender(),
                loginUserProfile.getIsPrivate()) ;
    }

    @Override
    @Transactional
    public UserResponse signup(SignUpRequest req){
        if(req.getEmail().isEmpty() || req.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(req.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already_exists");
        }

        User newUser = User.create(req.getEmail(), req.getPassword(), req.getPasswordConfirm(), req.getUserRole());

        UserProfile newUserProfile = UserProfile.create(req.getNickname(), req.getProfileImage(), req.getIntroduce(), req.getGender());

        User user = databaseUserRepository.save(newUser);

        newUserProfile.setUser(user);
        UserProfile profile = databaseUserProfileRepository.save(newUserProfile);

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUserRole(),
                profile.getNickname(),
                profile.getProfileImage(),
                profile.getIntroduce(),
                profile.getGender(),
                profile.getIsPrivate());
    }

    @Override
    public UserResponse getUserById(Long user_id){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user_id);

        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile profile = findProfile.get();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUserRole(),
                profile.getNickname(),
                profile.getProfileImage(),
                profile.getIntroduce(),
                profile.getGender(),
                profile.getIsPrivate()
        );
    }

    @Override
    public UserResponse getUserByEmail(String email){
        Optional<User> findUser = databaseUserRepository.findUserByEmail(email);

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user.getId());

        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile profile = findProfile.get();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUserRole(),
                profile.getNickname(),
                profile.getProfileImage(),
                profile.getIntroduce(),
                profile.getGender(),
                profile.getIsPrivate()
        );
    }

    @Override
    @Transactional
    public UserInfoResponse updateUserInfo(Long user_id, UpdateUserInfoRequest req) {
        return updateUserInfo.update(user_id, req);
    }

    @Override
    @Transactional
    public UserInfoResponse updateUserPassword(Long user_id, UpdatePasswordRequest req){
       return updateUserPassword.update(user_id, req);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserNickname(Long user_id, UpdateUserNicknameRequest req){
        return updateUserNickname.update(user_id, req);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfileImage(Long user_id, UpdateUserProfileImageRequest req) {
        return updateUserProfileImage.update(user_id, req);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserIntroduce(Long user_id, UpdateUserIntroduceRequest req) {
        return updateUserIntroduce.update(user_id, req);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserGender(Long user_id, UpdateUserGenderRequest req){
        return updateUserGender.update(user_id, req);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfileIsPrivate(Long user_id, UpdateUserProfileIsPrivateRequest req){
        return updateUserProfileIsPrivate.update(user_id, req);
    }

    @Override
    @Transactional
    public UserResponse removeUser(Long user_id){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);
        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user_id);

        if(findUser.isEmpty() || findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        databaseUserRepository.deleteUserById(user_id);

        databaseUserProfileRepository.deleteProfileByUserId(user_id);

        User user = findUser.get();
        UserProfile profile = findProfile.get();

        return new UserResponse(user.getId(), user.getEmail(), user.getUserRole(), profile.getNickname(), profile.getProfileImage(), profile.getIntroduce(), profile.getGender(), profile.getIsPrivate());
    }

    // Pageable
    // List
    @Override
    public List<UserProfileResponse> searchAsList(String keyword) {
        List<UserProfile> list = databaseUserProfileRepository.findByNicknameContainingIgnoreCase(keyword);

        return list.stream().filter(profile -> profile.getDeletedAt() == null).map(profile -> new UserProfileResponse(profile.getId(), profile.getNickname(), profile.getProfileImage(), profile.getIntroduce(), profile.getGender(), profile.getIsPrivate())).toList();
    }

    // Page
    @Override
    public Page<UserProfileResponse> searchAsPage(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserProfile> profiles = databaseUserProfileRepository.findByNicknameContainingIgnoreCase(keyword, pageable);


        return profiles.map(profile -> new UserProfileResponse(profile.getId(), profile.getNickname(), profile.getProfileImage(), profile.getIntroduce(), profile.getGender(), profile.getIsPrivate()));
    }

    // Slice
    @Override
    public Slice<UserProfileResponse> searchAsSlice(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<UserProfile> profiles = databaseUserProfileRepository.findSliceByNicknameContainingIgnoreCase(keyword, pageable);

        return profiles.map(profile -> new UserProfileResponse(profile.getId(), profile.getNickname(), profile.getProfileImage(), profile.getIntroduce(), profile.getGender(), profile.getIsPrivate()));
    }

}
