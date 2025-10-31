package com.example.spring_restapi.service;

import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.repository.PostRepository;
import com.example.spring_restapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository databasePostRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository databasePostRepository, UserRepository userRepository){
        this.databasePostRepository = databasePostRepository;
        this.userRepository = userRepository;
    }

    public Post write(CreatePostRequest req){
        if(req.getTitle().isEmpty() || req.getContent().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = userRepository.findUserById(req.getAuthor_id());
        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post newPost = new Post(null, req.getAuthor_id(), req.getTitle(), req.getContent(), req.getImages(),0, LocalDateTime.now());

        // 로그인된 유저인지 토큰 확인 로직 추가 예정

        return databasePostRepository.save(newPost);
    }

    public Post getPostByPostId(Long post_id){
        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return findPost.get();
    }

    public List<Post> getPostByAuthorId(Long authorId) {
        List<Post> posts = databasePostRepository.findPostByPostAuthorId(authorId);
        if (posts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
        return posts;
    }

    public List<Post> findAllPosts(){
        return databasePostRepository.findAllPost();
    }

    public List<Post> getPostsOfPage(int page, int size){
        if(page == 0 || size > 10){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<List<Post>> findPost = databasePostRepository.findPostsOfPage(page, size);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
        // 권한 확인 로직 추가 예정
        return findPost.get();
    }

    public Post updatePost(Long post_id, UpdatePostRequest req){
        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        // 작성자만 수정 가능
        if(!findPost.get().getAuthor_id().equals(req.getUser_id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Post data = findPost.get();

        data.setTitle(req.getTitle());
        data.setContent(req.getContent());
        data.setImages(req.getImages());

        data.setRewriteDate(LocalDateTime.now());

        Optional<Post> updatePost =  databasePostRepository.update(data);
        if(updatePost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        return updatePost.get();
    }

    public Post deletePost(Long post_id, Long user_id){
        Optional<Post> findPost = databasePostRepository.findPostByPostId(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post find = findPost.get();
        // 작성자만 삭제 가능

        if(!find.getAuthor_id().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        return databasePostRepository.deletePostByPostId(post_id);
    }

}
