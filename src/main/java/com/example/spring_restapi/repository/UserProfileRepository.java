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

    @Query("""
            select p
            from UserProfile p
            where p.user.id = :user_id
            and p.deletedAt IS NULL
            """)
    Optional<UserProfile> findProfileByUserId(Long user_id);

    @Query("""
            select p
            from UserProfile p
            where p.nickname =:nickname
            and p.deletedAt IS NULL
            """)
    Optional<List<UserProfile>> findProfileByNickname(String nickname);

    @Modifying
    @Query("""
            update UserProfile p
            set p.nickname = :nickname,
                p.profileImage = :profileImage,
                p.introduce = :introduce,
                p.isPrivate = :isPrivate,
                p.gender = :gender
            where p.id = :id
            """)
    void update(Long id, String nickname, String profileImage, String introduce, Boolean isPrivate, String gender);

    @Modifying
    @Query("""
            update UserProfile p
            set p.nickname = :nickname
            where p.user.id = :user_id
            """)
    void updateNickname(Long user_id, String nickname);

    @Modifying
    @Query("""
            update UserProfile p
            set p.profileImage = :profileImage
            where p.user.id = :user_id
            """)
    void updateProfileImage(Long user_id, String profileImage);

    @Modifying
    @Query("""
            update UserProfile p
            set p.introduce = :introduce
            where p.user.id = :user_id
            """)
    void updateIntroduce(Long user_id, String introduce);

    @Modifying
    @Query("""
            update UserProfile p
            set p.gender = :gender
            where p.user.id = :user_id
            """)
    void updateGender(Long user_id, String gender);

    @Modifying
    @Query("""
            update UserProfile p
            set p.isPrivate = :isPrivate
            where p.user.id = :user_id
            """)
    void updateIsPrivate(Long user_id, Boolean isPrivate);

    @Modifying
    @Query("""
            update UserProfile p
            set p.deletedAt = CURRENT_TIMESTAMP
            where p.user.id = :user_id
            """)
    void deleteProfileByUserId(Long user_id);
}
