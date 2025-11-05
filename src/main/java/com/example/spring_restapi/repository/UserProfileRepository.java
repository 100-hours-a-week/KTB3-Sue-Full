package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository {

    void save(UserProfile userProfile);

    List<UserProfile> findAllProfile();

    Optional<UserProfile> findProfileByUserId(Long user_id);

    Optional<List<UserProfile>> findProfileByNickname(String nickname);

    void update(UserProfile userProfile);

    void updateNickname(Long user_id, String nickname);

    void updateProfileImage(Long user_id, String profileImage);

    void updateIntroduce(Long user_id, String introduce);

    void updateGender(Long user_id, String gender);

    void updateIsPrivate(Long user_id, Boolean isPrivate);

    void deleteProfileByUserId(Long user_id);
}
