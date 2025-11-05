package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserProfileImageRequest;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Qualifier("updateUserProfileImage")
public class UpdateUserProfileImageImpl implements UpdateUserService<UserProfileResponse, UpdateUserProfileImageRequest>{
    private final UserProfileRepository databaseUserProfileRepository;


    public UpdateUserProfileImageImpl(UserProfileRepository databaseUserRepository) {
        this.databaseUserProfileRepository = databaseUserRepository;
    }

    @Override
    public UserProfileResponse update(Long user_id, UpdateUserProfileImageRequest req){
        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByUserId(user_id);

        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile userProfile = findUserProfile.get();

        userProfile.changeProfileImage(req.getProfile_image());

        databaseUserProfileRepository.updateProfileImage(user_id, req.getProfile_image());


        return new UserProfileResponse(userProfile.getId(), userProfile.getNickname(), userProfile.getProfileImage(), userProfile.getIntroduce(), userProfile.getGender(), userProfile.getIsPrivate());
    }
}
