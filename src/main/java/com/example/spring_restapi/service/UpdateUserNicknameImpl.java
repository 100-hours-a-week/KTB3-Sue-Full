package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.UpdateUserNicknameRequest;
import com.example.spring_restapi.dto.response.UserProfileResponse;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserProfile;
import com.example.spring_restapi.repository.UserProfileRepository;
import com.example.spring_restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("updateUserNickname")
public class UpdateUserNicknameImpl implements UpdateUserService<UserProfileResponse, UpdateUserNicknameRequest> {
    private final UserProfileRepository databaseUserProfileRepository;

    @Override
    @Transactional
    public UserProfileResponse update(Long user_id, UpdateUserNicknameRequest req){
        Optional<UserProfile> findUserProfile = databaseUserProfileRepository.findProfileByUserId(user_id);

        if(findUserProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile userProfile = findUserProfile.get();

        userProfile.changeNickname(req.getNickname());

        databaseUserProfileRepository.updateNickname(user_id, req.getNickname());

        return new UserProfileResponse(userProfile.getId(), userProfile.getNickname(), userProfile.getProfileImage(), userProfile.getIntroduce(), userProfile.getGender(), userProfile.getIsPrivate());
    }
}
