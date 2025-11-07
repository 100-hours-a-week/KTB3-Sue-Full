package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImagesRepository extends JpaRepository<PostImages, Long> {

    @Query("""
            select i
            from PostImages i
            where i.post.id = :post_id
            and i.deletedAt IS NULL
            """)
    List<PostImages> findPostImagesByPostId (Long post_id);

    @Query("""
            select i
            from PostImages i
            where i.isThumbnail = TRUE
            and i.deletedAt IS NULL
            """)
    List<PostImages> findAllThumbnail();

    @Query("""
            select i
            from PostImages i
            where i.post.id = :post_id
                and i.isThumbnail = TRUE
                and i.deletedAt IS NULL
            """)
    Optional<PostImages> findThumbnailByPostId(Long post_id);

    @Modifying
    @Query("""
            update PostImages i
            set i.deletedAt = CURRENT_TIMESTAMP
            where i.id = :id
            """)
    PostImages deletePostImagesById(Long id);

    @Modifying
    @Query("""
            update PostImages i
            set i.deletedAt = CURRENT_TIMESTAMP
            where i.post.id = :post_id
            """)
    void deleteAllPostImagesByPostId(Long post_id);
}
