package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAllUser();

    Optional<User> findUserById(Long user_id);

    Optional<User> findUserByEmail(String email);

    Optional<User> update(User user);

    User deleteUserById(Long user_id);
}
