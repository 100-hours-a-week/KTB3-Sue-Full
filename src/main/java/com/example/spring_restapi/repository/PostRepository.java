package com.example.spring_restapi.repository;

import com.example.spring_restapi.dto.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PostRepository {
    private final Map<Long, Post> postMap = new LinkedHashMap<>();
    private long sequence;

    public PostRepository(){
        sequence = 0;
        List<String> images1 = new ArrayList<>();
        images1.add("image1.jpg");
        images1.add("image2.jpg");

        List<String> images2 = new ArrayList<>();
        images1.add("image2.jpg");
        images1.add("image3.jpg");

        Set<Long> users1 = new HashSet<>();
        users1.add(1L);
        users1.add(2L);

        Set<Long> users2 = new HashSet<>();
        users1.add(2L);
        users1.add(3L);

        Post post1 = new Post(1L, 1L, "1week TIL","my homework post1...", images1, users1, 2, LocalDateTime.parse("2025-10-16T10:00:00"));
        Post post2 = new Post(2L, 2L, "3 week TIL", "my homework post2...TIL", images1, users2, 5, LocalDateTime.parse("2025-10-15T10:00:00"));
        Post post3 = new Post(3L, 3L, "No...", "my homework post...GOOD", images1, users1, 10, LocalDateTime.parse("2025-10-15T12:00:00"));

        sequence++;
        postMap.put(post1.getPost_id(), post1);
        sequence++;
        postMap.put(post2.getPost_id(), post2);
        sequence++;
        postMap.put(post3.getPost_id(), post3);
    }

    public Post save(Post post){
        sequence++;
        if(Optional.ofNullable(post.getPost_id()).isEmpty()){
            post.setPost_id(sequence);
        }
        postMap.put(post.getPost_id(), post);
        return postMap.get(post.getPost_id());
    }

    public Optional<Post> update(Post post){
        for(Map.Entry<Long, Post> entry : postMap.entrySet()){
            Post find = entry.getValue();
            if(find.getPost_id().equals(post.getPost_id())){
                return Optional.ofNullable(postMap.put(post.getPost_id(), post));
            }
        }
        return Optional.empty();
    }

    public List<Post> findAllPost(){
        List<Post> posts = new ArrayList<>();
        for(Long post_id: postMap.keySet()){
            Post post = postMap.get(post_id);
            posts.add(post);
        }
        posts.sort(Comparator.comparing(Post::getDate).reversed());
        return posts;
    }

    public List<Post> findPostsOfPage(int page, int size){
        List<Post> posts = new ArrayList<>(postMap.values());

        // 최신순 정렬
        posts.sort(Comparator.comparing(Post::getDate).reversed());

        int total = posts.size();

        int startIndex = (page - 1) * size; // 페이지 1 -> 인덱스 0부터 시작
        int endIndex = startIndex + size;


        if (startIndex >= total) return Collections.emptyList();

        if(endIndex >= total) {
            endIndex = total;
        }

        return posts.subList(startIndex, endIndex);
    }

    public Optional<Post> findPostByPostId(Long post_id){
        return Optional.ofNullable(postMap.get(post_id));
    }

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

    public Post deletePostByPostId(Long post_id){
        return postMap.remove(post_id);
    }
}
