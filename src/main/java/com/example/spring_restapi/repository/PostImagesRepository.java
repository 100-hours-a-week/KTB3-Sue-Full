package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.PostImages;

import java.util.List;
import java.util.Optional;

public interface PostImagesRepository {

    void save(PostImages postImages);

    List<PostImages> findPostImagesByPostId (Long post_id);

    List<PostImages> findAllThumbnail();

    Optional<PostImages> findThumbnailByPostId(Long post_id);

    PostImages deletePostImagesById(Long id);

    List<PostImages> deleteAllPostImagesByPostId(Long post_id);
}
