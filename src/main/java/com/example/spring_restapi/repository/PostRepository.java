package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAllPost();

    Optional<List<Post>> findPostsOfPage(int page, int size);

    Optional<Post> findPostByPostId(Long post_id);

    List<Post> findPostByPostAuthorId(Long author_id);

    Optional<Post> update(Post post);

    Post deletePostByPostId(Long post_id);

}
