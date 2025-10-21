package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Map<Long, User> userMap = new LinkedHashMap<>();
    private long sequence;

    public UserRepository(){
        sequence = 0;
        User user1 = new User(null, "osj1405@naver.com", "osj1405", "sue", null, null, null);
        User user2 = new User(null, "osujin35@naver.com", "osujin35", "sujin", null, null, null);
        User user3 = new User(null, "duckjin1405@gmail.com", "duckjin1405", "osj", null, null, null);

        save(user1);
        save(user2);
        save(user3);
    }

    public User save(User user){
        sequence++;
        if(Optional.ofNullable(user.getUser_id()).isEmpty()){
            user.setUser_id(sequence);
        }
        userMap.put(user.getUser_id(), user);
        return userMap.get(user.getUser_id());
    }

    public List<User> findAllUser(){
        List<User> users = new ArrayList<>();
        for(Long user_id: userMap.keySet()){
            User user = userMap.get(user_id);
            users.add(user);
        }
        return users;
    }

    public Optional<User> findUserById(Long user_id){
        return Optional.ofNullable(userMap.get(user_id));
    }

    public Optional<User> findUserByEmail(String email){
        for(Map.Entry<Long, User> entry: userMap.entrySet()){
            User user = entry.getValue();
            if(user.getEmail().equals(email)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> update(User user){
        return Optional.ofNullable(userMap.put(user.getUser_id(), user));
    }

    public User deleteUserById(Long user_id){
        return userMap.remove(user_id);
    }
}
