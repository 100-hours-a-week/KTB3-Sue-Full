package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("""
            update Comment c
            set c.content = :content,
                c.updatedAt = CURRENT_TIMESTAMP
            where c.id = :id and c.deletedAt IS NULL
            """)
    void updateComment(Long id, String content);

    @Modifying
    @Query("""
            update Comment c
            set c.deletedAt = CURRENT_TIMESTAMP
            where c.id = :id
            """)
    void deleteComment(Long id);

    @Modifying
    @Query("""
            update Comment c
            set c.deletedAt = CURRENT_TIMESTAMP
            where c.post.id = :post_id
            """)
    void deleteCommentByPostId(Long post_id);

    @Query("""
            select c
            from Comment c
            where c.id = :comment_id
            """)
    Optional<Comment> findCommentById(Long comment_id);

    @Query("""
            select c
            from Comment c
            where c.post.id = :post_id
            """)
    Slice<Comment> findCommentsByPostId(Long post_id, Pageable pageable);
}
