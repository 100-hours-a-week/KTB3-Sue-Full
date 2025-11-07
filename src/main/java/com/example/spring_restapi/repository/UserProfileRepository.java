package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findProfileByUserId(Long user_id);

    Optional<List<UserProfile>> findProfileByNickname(String nickname);

    @Modifying
    @Query("""
            update UserProfile p
            set p.nickname = :nickname,
                p.profileImage = :profileImage,
                p.introduce = :introduce,
                p.isPrivate = :isPrivate,
                p.gender = :gender,
                p.updatedAt = CURRENT_TIMESTAMP
            where p.id = :id
            """)
    void update(Long id, String nickname, String profileImage, String introduce, Boolean isPrivate, String gender);

    @Modifying
    @Query("""
            update UserProfile p
            set p.nickname = :nickname,
                p.updatedAt = CURRENT_TIMESTAMP
            where p.user.id = :user_id
            """)
    void updateNickname(Long user_id, String nickname);

    @Modifying
    @Query("""
            update UserProfile p
            set p.profileImage = :profileImage,
                p.updatedAt = CURRENT_TIMESTAMP
            where p.user.id = :user_id
            """)
    void updateProfileImage(Long user_id, String profileImage);

    @Modifying
    @Query("""
            update UserProfile p
            set p.introduce = :introduce,
                p.updatedAt = CURRENT_TIMESTAMP
            where p.user.id = :user_id
            """)
    void updateIntroduce(Long user_id, String introduce);

    @Modifying
    @Query("""
            update UserProfile p
            set p.gender = :gender,
                p.updatedAt = CURRENT_TIMESTAMP
            where p.user.id = :user_id
            """)
    void updateGender(Long user_id, String gender);

    @Modifying
    @Query("""
            update UserProfile p
            set p.isPrivate = :isPrivate,
                p.updatedAt = CURRENT_TIMESTAMP
            where p.user.id = :user_id
            """)
    void updateIsPrivate(Long user_id, Boolean isPrivate);

    void deleteProfileByUserId(Long user_id);
}
