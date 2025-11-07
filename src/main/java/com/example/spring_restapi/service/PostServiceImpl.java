package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.response.PostImageResponse;
import com.example.spring_restapi.dto.response.PostResponse;
import com.example.spring_restapi.model.*;
import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository databasePostRepository;
    private final PostImagesRepository databasePostImageRepository;
    private final UserRepository databaseUserRepository;
    private final UserProfileRepository databaseUserProfileRepository;
    private final LikeRepository databaseLikeRepository;
    private final CommentRepository databaseCommentRepository;

    @Override
    @Transactional
    public PostResponse write(CreatePostRequest req) {
        if (req.getTitle().isEmpty() || req.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getAuthor_id());
        if (findUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        User user = findUser.get();

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user.getId());
        if (findProfile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile profile = findProfile.get();
        Post newPost = new Post(req.getTitle(), req.getContent(), req.getPostType(), user);

        // 로그인된 유저인지 토큰 확인 로직 추가 예정

        if (!req.getImages().isEmpty()) {
            List<PostImages> images = req.getImages().stream().map(image -> PostImages.create(newPost, image, false)).toList();
            images.getFirst().changeIsThumbNail();

            for (PostImages image : images) {
                newPost.addImages(image);
            }
        }

        databasePostRepository.save(newPost);

        List<PostImageResponse> postImages = newPost
                .getImages()
                .stream()
                .map(image -> new PostImageResponse(newPost.getId(), image.getImage_url(), image.getIsThumbnail()))
                .collect(Collectors.toList());

        return new PostResponse(
                newPost.getId(),
                profile.getNickname(),
                profile.getProfileImage(),
                newPost.getTitle(),
                newPost.getContent(),
                postImages,
                newPost.getPostType(),
                0,
                0,
                0,
                newPost.getCreatedAt(),
                newPost.getUpdatedAt());
    }

    @Override
    @Transactional
    public PostResponse getPostByPostId(Long post_id) {
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
        if (findPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Post post = findPost.get();
        User author = post.getAuthor();

        databasePostRepository.readPostBySomeone(post.getId());

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(author.getId());

        if (findProfile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile profile = findProfile.get();

        List<PostImageResponse> postImages = databasePostImageRepository.findPostImagesByPostId(post_id).stream().map(image -> new PostImageResponse(post_id, image.getImage_url(), image.getIsThumbnail())).collect(Collectors.toList());

        return new PostResponse(
                post.getId(),
                profile.getNickname(),
                profile.getProfileImage(),
                post.getTitle(),
                post.getContent(),
                postImages,
                post.getPostType(),
                post.getWatch(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    @Override
    public List<PostResponse> getPostByAuthorId(Long authorId) {
        List<Post> posts = databasePostRepository.findPostByPostAuthor_Id(authorId);
        if (posts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<UserProfile> authorProfile = databaseUserProfileRepository.findProfileByUserId(authorId);
        if (authorProfile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        UserProfile profile = authorProfile.get();

        return posts.stream().map(post -> {
            List<PostImageResponse> images = post.getImages().stream().map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

                    return new PostResponse(
                            post.getId(),
                            profile.getNickname(),
                            profile.getProfileImage(),
                            post.getTitle(),
                            post.getContent(),
                            images,
                            post.getPostType(),
                            post.getWatch(),
                            post.getLikeCount(),
                            post.getCommentCount(),
                            post.getCreatedAt(),
                            post.getUpdatedAt());
                }
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PostResponse> getPostsOfPage(int page, int size){
        if(page == 0 || size > 10){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        List<Post> findPost = databasePostRepository.findPostsOfPage(page, size);
        // 권한 확인 로직 추가 예

        return findPost.stream().map(post -> {
            UserProfile profile = databaseUserProfileRepository.findProfileByUserId(post.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));

            List<PostImageResponse> images = post.getImages().stream().map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

            return new PostResponse(
                    post.getId(),
                    profile.getNickname(),
                    profile.getProfileImage(),
                    post.getTitle(),
                    post.getContent(),
                    images,
                    post.getPostType(),
                    post.getWatch(),
                    post.getLikeCount(),
                    post.getCommentCount(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            );
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long post_id, UpdatePostRequest req){
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getAuthor_id());

        if(findUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
        // 작성자만 수정 가능
        if(!findPost.get().getAuthor().getId().equals(req.getAuthor_id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(req.getAuthor_id());


        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post data = findPost.get();

        UserProfile profile = findProfile.get();

        data.changeTitle(req.getTitle());
        data.changeContent(req.getContent());
        data.changePostType(req.getPostType());

        databasePostRepository.update(data.getId(), data.getTitle(), data.getContent(), data.getPostType());

        if (!req.getImages().isEmpty()) {
            databasePostImageRepository.deleteAllPostImagesByPostId(data.getId());
            List<PostImages> images = req.getImages().stream().map(image -> PostImages.create(data, image, false)).toList();
            images.getFirst().changeIsThumbNail();

            for (PostImages image : images) {
                data.addImages(image);
            }
        }

        List<PostImageResponse> images = data.getImages().stream().map(image -> new PostImageResponse(data.getId(), image.getImage_url(),image.getIsThumbnail())).collect(Collectors.toList());

        return new PostResponse(data.getId(), profile.getNickname(), profile.getProfileImage(), data.getTitle(), data.getContent(), images, data.getPostType(), data.getWatch(), data.getLikeCount(), data.getCommentCount(), data.getCreatedAt(), data.getUpdatedAt());
    }

    @Override
    @Transactional
    public PostResponse deletePost(Long post_id, Long user_id){
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
        if(findPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post post = findPost.get();

        // 작성자만 삭제 가능
        if(!post.getAuthor().getId().equals(user_id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(user_id);
        if(findProfile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        databasePostRepository.deletePostById(post_id);

        UserProfile profile = findProfile.get();

        List<PostImageResponse> postImages = databasePostImageRepository.deleteAllPostImagesByPostId(post_id).stream().map(postImage -> new PostImageResponse(post_id, postImage.getImage_url(), postImage.getIsThumbnail())).toList();

        databaseLikeRepository.deleteLikePostInfo(post_id);
        databaseCommentRepository.deleteCommentByPostId(post_id);

        return new PostResponse(post.getId(), profile.getNickname(), profile.getProfileImage(), post.getTitle(), post.getContent(), postImages, post.getPostType(), post.getWatch(), post.getLikeCount(), post.getCommentCount(), post.getCreatedAt(), post.getUpdatedAt());
    }

}
