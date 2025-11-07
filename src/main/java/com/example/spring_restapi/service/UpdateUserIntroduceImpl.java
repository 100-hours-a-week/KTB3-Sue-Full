package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserIntroduceRequest;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("updateUserIntroduce")
public class UpdateUserIntroduceImpl implements UpdateUserService<UserProfileResponse, UpdateUserIntroduceRequest> {
    private final UserProfileRepository databaseUserProfileRepository;

    @Override
    @Transactional
    public UserProfileResponse update(Long user_id, UpdateUserIntroduceRequest req){
        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByUserId(user_id);

        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile userProfile = findUserProfile.get();

        userProfile.changeIntroduce(req.getIntroduce());
        databaseUserProfileRepository.updateIntroduce(user_id, req.getIntroduce());

        return new UserProfileResponse(userProfile.getId(), userProfile.getNickname(), userProfile.getProfileImage(), userProfile.getIntroduce(), userProfile.getGender(), userProfile.getIsPrivate());
    }
}
