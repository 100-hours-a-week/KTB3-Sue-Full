package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserProfileIsPrivateRequest;
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
@Qualifier("updateUserProfileIsPrivate")
public class UpdateUserProfileIsPrivateImpl implements UpdateUserService<UserProfileResponse, UpdateUserProfileIsPrivateRequest>{
    private final UserProfileRepository databaseUserProfileRespository;

    @Override
    @Transactional
    public UserProfileResponse update(Long user_id, UpdateUserProfileIsPrivateRequest req){
        Optional<UserProfile> findUserProfile = databaseUserProfileRespository.findProfileByUserId(user_id);

        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile userProfile = findUserProfile.get();
        userProfile.changeAccountIsPrivate(req.getIsPrivate());

        databaseUserProfileRespository.updateIsPrivate(user_id, req.getIsPrivate());
        return new UserProfileResponse(userProfile.getId(), userProfile.getNickname(), userProfile.getProfileImage(), userProfile.getIntroduce(), userProfile.getGender(), userProfile.getIsPrivate());
    }
}
