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

    @Query("""
            select u
            from User u
            where u.id = :user_id
            and u.deletedAt IS NULL
            """)
    Optional<User> findUserById(Long user_id);

    @Query("""
            select u
            from User u
            where u.email = :email
            and u.deletedAt IS NULL
            """)
    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("""
            update User u
            set u.email = :email,
                u.password = :password,
                u.userRole = :userRole
            """)
    void update(String email, String password, UserRole userRole);

    @Modifying
    @Query("""
            update User u
            set u.email = :email
            where u.id = :user_id
            """)
    void updateEmail(Long user_id, String email);

    @Modifying
    @Query("""
            update User u
            set u.password = :password
            where u.id = :user_id
            """)
    void updatePassword(Long user_id, String password);

    @Modifying
    @Query("""
            update User u
            set u.userRole = :userRole
            where u.id = :user_id
            """)
    void updateUserRole(Long user_id, UserRole userRole);

    @Modifying
    @Query("""
            update User u
            set u.deletedAt = CURRENT_TIMESTAMP
            where u.id = :user_id
            """)
    void deleteUserById(Long user_id);
}
