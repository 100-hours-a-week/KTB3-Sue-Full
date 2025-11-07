package com.example.spring_restapi.repository;

import com.example.spring_restapi.dto.response.LikeResponse;
import com.example.spring_restapi.model.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("""
            select l
            from Like l
            where l.post.id = :post_id
                and l.deletedAt IS NULL
            """)
    Slice<Like> findLikesByPostId(Long post_id, Pageable pageable);

    @Query("""
            select l
            from Like l
            where l.user.id = :user_id
                and l.deletedAt IS NULL
            """)
    List<Like> findLikesByUserId(Long user_id);

    @Query("""
            select l
            from Like l
            where l.user.id = :user_id
                and l.post.id = :post_id
                and l.deletedAt IS NULL
            """)
    Optional<Like> findLikeOfPostByUserId(Long post_id, Long user_id);

    @Modifying
    @Query("""
            update Like l
            set l.deletedAt = CURRENT_TIMESTAMP
            where l.id = :id
            """)
    void deleteLike(Long id);

    @Modifying
    @Query("""
            update Like l
            set l.deletedAt = CURRENT_TIMESTAMP
            where l.post.id = :post_id
            and l.user.id = :user_id
            """)
    void deleteLikeOfPostByUserId(Long post_id, Long user_id);

    @Modifying
    @Query("""
            update Like l
            set l.deletedAt = CURRENT_TIMESTAMP
            where l.post.id = :post_id
            """)
    void deleteLikePostInfo(Long post_id);
}
