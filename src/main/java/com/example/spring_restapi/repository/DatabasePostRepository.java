package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DatabasePostRepository implements PostRepository {
    private final ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private long sequence;

    public DatabasePostRepository(){
        sequence = 0;
        List<String> images1 = new ArrayList<>();
        images1.add("image1.jpg");
        images1.add("image2.jpg");

        List<String> images2 = new ArrayList<>();
        images1.add("image2.jpg");
        images1.add("image3.jpg");

        Post post1 = new Post(null, 1L, "1week TIL","my homework post1...", images1, 2, LocalDateTime.parse("2025-10-16T10:00:00"));
        Post post2 = new Post(null, 2L, "3 week TIL", "my homework post2...TIL", images1, 5, LocalDateTime.parse("2025-10-15T10:00:00"));
        Post post3 = new Post(null, 3L, "No...", "my homework post...GOOD", images2, 10, LocalDateTime.parse("2025-10-15T12:00:00"));

        save(post1);
        save(post2);
        save(post3);
    }

    @Override
    public Post save(Post post){
        sequence++;
        if(Optional.ofNullable(post.getPost_id()).isEmpty()){
            post.setPost_id(sequence);
        }
        postMap.put(post.getPost_id(), post);
        return postMap.get(post.getPost_id());
    }

    @Override
    public List<Post> findAllPost(){
        List<Post> posts = new ArrayList<>();
        for(Long post_id: postMap.keySet()){
            Post post = postMap.get(post_id);
            posts.add(post);
        }
        posts.sort(Comparator.comparing(Post::getDate).reversed());
        return posts;
    }

    @Override
    public Optional<List<Post>> findPostsOfPage(int page, int size){
        List<Post> posts = new ArrayList<>(postMap.values());

        // 최신순 정렬
        posts.sort(Comparator.comparing(Post::getDate).reversed());

        int total = posts.size();

        int startIndex = (page - 1) * size; // 페이지 1 -> 인덱스 0부터 시작
        int endIndex = startIndex + size;


        if (startIndex >= total) return Optional.empty();

        if(endIndex >= total) {
            endIndex = total;
        }

        return Optional.of(posts.subList(startIndex, endIndex));
    }

    @Override
    public Optional<Post> findPostByPostId(Long post_id){
        return Optional.ofNullable(postMap.get(post_id));
    }

    @Override
    public List<Post> findPostByPostAuthorId(Long author_id){
        List<Post> findPost = new ArrayList<>();
        for(Map.Entry<Long, Post> entry : postMap.entrySet()){
            Post post = entry.getValue();
            if(post.getAuthor_id().equals(author_id)){
                findPost.add(post);
            }
        }
        return findPost;
    }

    @Override
    public Optional<Post> update(Post post){
        return Optional.ofNullable(postMap.put(post.getPost_id(), post));
    }

    @Override
    public Post deletePostByPostId(Long post_id){
        return postMap.remove(post_id);
    }
}
