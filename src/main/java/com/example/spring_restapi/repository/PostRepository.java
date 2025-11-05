package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    List<Post> findAllPost();

    List<Post> findPostsOfPage(int page, int size);

    Optional<Post> findPostByPostId(Long post_id);

    List<Post> findPostByPostAuthorId(Long author_id);

    void update(Post post);

    void readPostBySomeone(Post post);

    void likeBySomeone(Long post_id);

    void unlikeBySomeone(Long post_id);

    void writeCommentBySomeone(Post post);

    void deleteCommentBySomeone(Post post);

    void deletePostByPostId(Long post_id);

}
