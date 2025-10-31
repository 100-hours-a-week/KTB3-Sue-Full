package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    public Post save(Post post);

    public List<Post> findAllPost();

    public Optional<List<Post>> findPostsOfPage(int page, int size);

    public Optional<Post> findPostByPostId(Long post_id);

    public List<Post> findPostByPostAuthorId(Long author_id);

    public Optional<Post> update(Post post);

    public Post deletePostByPostId(Long post_id);

}
