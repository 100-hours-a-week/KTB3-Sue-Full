package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.response.UserInfoResponse;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.dto.response.UserResponse;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.dto.request.*;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import com.example.spring_restapi.storage.S3Uploader;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository databaseUserRepository;
    private final UserProfileRepository databaseUserProfileRepository;

    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UpdateUserService<UserInfoResponse, UpdateUserInfoRequest> updateUserInfo;
    private final UpdateUserService<UserInfoResponse, UpdatePasswordRequest> updateUserPassword;
    private final UpdateUserService<UserProfileResponse, UpdateUserNicknameRequest> updateUserNickname;
    private final UpdateUserService<UserProfileResponse, UpdateUserProfileImageRequest> updateUserProfileImage;
    private final UpdateUserService<UserProfileResponse, UpdateUserIntroduceRequest> updateUserIntroduce;
    private final UpdateUserService<UserProfileResponse, UpdateUserGenderRequest> updateUserGender;
    private final UpdateUserService<UserProfileResponse, UpdateUserProfileIsPrivateRequest> updateUserProfileIsPrivate;

    private final S3Uploader s3Uploader;

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
                loginUserProfile.getIsPrivate()
        );
    }

    @Override
    public UserResponse makeUserResponse(User user){
        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user.getId());

        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found");
        }

        UserProfile userProfile = findProfile.get();
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUserRole(),
                userProfile.getNickname(),
                userProfile.getProfileImage(),
                userProfile.getIntroduce(),
                userProfile.getGender(),
                userProfile.getIsPrivate()
        );
    }

    @Override
    @Transactional
    public UserResponse signup(SignUpRequest req) throws IOException {
        if(req.getEmail().isEmpty() || req.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserByEmail(req.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already_exists");
        }

        MultipartFile profileImage = req.getProfileImage();

        String profileImageUrl  = s3Uploader.upload(profileImage, "profile-image");

        String encodedPassword = bCryptPasswordEncoder.encode(req.getPassword());
        String encodedPasswordConfirm = bCryptPasswordEncoder.encode(req.getPasswordConfirm());

        User newUser = User.create(req.getEmail(), encodedPassword, encodedPasswordConfirm, req.getUserRole());

        UserProfile newUserProfile = UserProfile.create(req.getNickname(), profileImageUrl, req.getIntroduce(), req.getGender());

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
    public UserProfileResponse updateUserNicknameAndProfileImage(UpdateUserNicknameProfileImageReqeust req) throws IOException {
        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByUserId(req.getUser_id());

        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile userProfile = findUserProfile.get();

        if(!req.getNewProfileImage().isEmpty() && req.getNewProfileImage() != null){
            // profile image processing
            String currentProfileImage = req.getCurrentProfileImage();

            System.out.println("current " + req.getCurrentProfileImage());

            s3Uploader.delete(currentProfileImage);

            System.out.println("원래 이미지 삭제 완료");

            MultipartFile newProfileImage = req.getNewProfileImage();

            String newProfileImageUrl = s3Uploader.upload(newProfileImage, "profile-image");

            userProfile.changeProfileImage(newProfileImageUrl);
        }

        System.out.println(req.getNewNickname());
        if(!req.getNewNickname().isEmpty()){
            userProfile.changeNickname(req.getNewNickname());
        }

        databaseUserProfileRepository.updateNicknameAndProfileImage(req.getUser_id(), userProfile.getNickname(), userProfile.getProfileImage());

        return new UserProfileResponse(
                userProfile.getId(),
                userProfile.getNickname(),
                userProfile.getProfileImage(),
                userProfile.getIntroduce(),
                userProfile.getGender(),
                userProfile.getIsPrivate()
        );
    }

    @Override
    @Transactional
    public UserResponse removeUser(Long user_id){
        Optional<User> findUser = databaseUserRepository.findUserById(user_id);
        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user_id);

        if(findUser.isEmpty() || findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        // 프로필 이미지 로컬 스토리지에서 지우기

        if(!findProfile.get().getProfileImage().isEmpty()){
            s3Uploader.delete(findProfile.get().getProfileImage());
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

    @Override
    public Boolean checkEmailConflict(EmailCheckRequest req) {
        if (req.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }
        Optional<User> findUser = databaseUserRepository.findUserByEmail(req.getEmail());
        findUser.ifPresent(user -> System.out.println(user.getEmail()));

        return findUser.isPresent();
    }

    @Override
    public Boolean checkNicknameConflict(NicknameCheckRequest req) {
        if (req.getNickname().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByNickname(req.getNickname());
        findUserProfile.ifPresent(user -> System.out.println(findUserProfile.get().getNickname()));

        return findUserProfile.isPresent();
    }
}
