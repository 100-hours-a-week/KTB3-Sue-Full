package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DatabaseUserRepository {

    @PersistenceContext
    private EntityManager em;

    protected DatabaseUserRepository() {}

//    @Override
//    @Transactional
//    public Optional<User> save(User newUser){
//        em.persist(newUser);
//        return null;
//    }

//    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUser(){
        TypedQuery<User> query = em.createQuery(
                "select u from User u where u.deletedAt IS NULL",
                User.class);

        return query.getResultList();
    }

//    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long user_id){
        List<User> query = em.createQuery("""
                select u
                from User u
                where u.id = :user_id
                and u.deletedAt IS NULL
                """, User.class)
                .setParameter("user_id", user_id)
                .getResultList();

        return query.stream().findFirst();
    }

//    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email){
        TypedQuery<User> query = em.createQuery(
                "select u from User u where u.email = :email and u.deletedAt IS NULL" , User.class
        );

        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

//    @Override
    @Transactional
    public void update(User user){
        User updateUser = em.find(User.class, user.getId());
        updateUser.changeEmail(user.getEmail());
        updateUser.changePassword(user.getPassword(), user.getPasswordConfirm());
        updateUser.changeUserRole(user.getUserRole());
    }


//    @Override
    @Transactional
    public void updateEmail(Long user_id, String email){
        User updateUser = em.find(User.class, user_id);
        updateUser.changeEmail(email);
    }

//    @Override
    @Transactional
    public void updatePassword(Long user_id, String password, String passwordConfirm){
        User updateUser = em.find(User.class, user_id);
        updateUser.changePassword(password, passwordConfirm);
    }

//    @Override
    @Transactional
    public void updateUserRole(Long user_id, UserRole userRole){
        User updateUser = em.find(User.class, user_id);
        updateUser.changeUserRole(userRole);
    }

//    @Override
    @Transactional
    public void deleteUserById(Long user_id){
        User deleteUser = em.find(User.class, user_id);
        deleteUser.setDeletedAt(LocalDateTime.now());
    }
}
