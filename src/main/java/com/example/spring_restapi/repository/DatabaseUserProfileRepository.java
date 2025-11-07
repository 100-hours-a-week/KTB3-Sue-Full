package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseUserProfileRepository {

    @PersistenceContext
    EntityManager em;

    protected DatabaseUserProfileRepository() {}

//    @Override
    @Transactional
    public void save(UserProfile userProfile) {
       em.persist(userProfile);
    }

//    @Override
    @Transactional(readOnly = true)
    public List<UserProfile> findAllProfile() {
        TypedQuery<UserProfile> query = em.createQuery("""
                select p
                from UserProfile p
                where p.deletedAt IS NULL
                """, UserProfile.class);

        return query.getResultList();
    }

//    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfile> findProfileByUserId(Long user_id){
        List<UserProfile> result = em.createQuery("""
                select p
                from UserProfile p
                where p.user.id = :user_id
                and p.deletedAt IS NULL
                """, UserProfile.class)
                .setParameter("user_id", user_id)
                .getResultList();

        return result.stream().findFirst();
    }

//    @Override
    @Transactional(readOnly = true)
    public Optional<List<UserProfile>> findProfileByNickname(String nickname) {
        TypedQuery<UserProfile> query = em.createQuery("""
                select p
                from UserProfile p
                where p.nickname = :nickname
                and p.deletedAt IS NULL
                """, UserProfile.class);
        query.setParameter("nickname", nickname);

        return Optional.ofNullable(query.getResultList());
    }

//    @Override
    @Transactional
    public void update(UserProfile userProfile) {
        UserProfile updateProfile = em.find(UserProfile.class, userProfile.getId());

        updateProfile.changeNickname(userProfile.getNickname());
        updateProfile.changeProfileImage(userProfile.getProfileImage());
        updateProfile.changeIntroduce(userProfile.getIntroduce());
        updateProfile.changeGender(userProfile.getGender());
        updateProfile.changeAccountIsPrivate(userProfile.getIsPrivate());
    }

//    @Override
    @Transactional
    public void updateNickname(Long user_id, String nickname){

        UserProfile userProfile = findProfileByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));

        userProfile.changeNickname(nickname);

    }

//    @Override
    @Transactional
    public void updateProfileImage(Long user_id, String profileImage){
        UserProfile userProfile = findProfileByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
        userProfile.changeProfileImage(profileImage);
    }

//    @Override
    @Transactional
    public void updateIntroduce(Long user_id, String introduce){
        UserProfile userProfile = findProfileByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
        userProfile.changeIntroduce(introduce);
    }

//    @Override
    @Transactional
    public void updateGender(Long user_id, String gender){
        UserProfile userProfile = findProfileByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
        userProfile.changeGender(gender);
    }

//    @Override
    @Transactional
    public void updateIsPrivate(Long user_id, Boolean isPrivate){
        UserProfile userProfile = findProfileByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
        userProfile.changeAccountIsPrivate(isPrivate);
    }

//    @Override
    @Transactional
    public void deleteProfileByUserId(Long user_id) {
        UserProfile removeProfile = findProfileByUserId(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found0"));

        removeProfile.setDeletedAt(LocalDateTime.now());
    }
}
