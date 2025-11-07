package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long user_id);

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("""
            update User u
            set u.email = :email,
                u.password = :password,
                u.userRole = :userRole,
                u.updatedAt = CURRENT_TIMESTAMP
            """)
    void update(String email, String password, UserRole userRole);

    @Modifying
    @Query("""
            update User u
            set u.email = :email,
                u.updatedAt = CURRENT_TIMESTAMP
            where u.id = :user_id
            """)
    void updateEmail(Long user_id, String email);

    @Modifying
    @Query("""
            update User u
            set u.password = :password,
                u.updatedAt = CURRENT_TIMESTAMP
            where u.id = :user_id
            """)
    void updatePassword(Long user_id, String password);

    @Modifying
    @Query("""
            update User u
            set u.userRole = :userRole,
                u.updatedAt = CURRENT_TIMESTAMP
            where u.id = :user_id
            """)
    void updateUserRole(Long user_id, UserRole userRole);

    void deleteUserById(Long user_id);
}
