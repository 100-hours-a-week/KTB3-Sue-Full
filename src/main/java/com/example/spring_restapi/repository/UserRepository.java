package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    List<User> findAllUser();

    Optional<User> findUserById(Long user_id);

    Optional<User> findUserByEmail(String email);

    void update(User user);

    void updateEmail(Long user_id, String email);

    void updatePassword(Long user_id, String password, String passwordConfirm);

    void updateUserRole(Long user_id, UserRole userRole);

    void deleteUserById(Long user_id);
}
