package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostImagesRepository extends JpaRepository<PostImages, Long> {

    List<PostImages> findPostImagesByPostId (Long post_id);

    @Query("""
            select i
            from PostImages i
            where i.isThumbnail = TRUE
            and i.deletedAt IS NULL
            """)
    List<PostImages> findAllThumbnail();

    Optional<PostImages> findThumbnailByPostId(Long post_id);

    PostImages deletePostImagesById(Long id);

    List<PostImages> deleteAllPostImagesByPostId(Long post_id);
}
