package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.response.PostImageResponse;
import com.example.spring_restapi.dto.response.PostResponse;
import com.example.spring_restapi.model.*;
import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.*;
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
    public PostResponse write(CreatePostRequest req) throws IOException {
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

        List<PostImageResponse> postImages = new ArrayList<>();

        if (req.getImages() != null && !req.getImages().isEmpty()) {
            List<MultipartFile> imageFiles = req.getImages();
            List<String> fileNames = imageFiles.stream().map(MultipartFile::getOriginalFilename).toList();


            List<String> newFileNames = new ArrayList<>();

            for(MultipartFile imageFile : imageFiles){
                String fileOriginName = imageFile.getOriginalFilename();

                UUID uuid = UUID.randomUUID();
                String extension = null;

                if(fileOriginName != null){
                    extension = fileOriginName.substring(fileOriginName.lastIndexOf("."));
                }

                String newFileName = uuid + extension;
                newFileNames.add(newFileName);

                String filePath = "/Users/ohsujin/KTB/Spring/spring-restapi/src/main/resources/static/upload/postImage/" + newFileName;

                imageFile.transferTo(new File(filePath));
            }

            List<PostImages> images = newFileNames.stream().map(file -> PostImages.create(newPost, file, false)).toList();
            images.getFirst().changeIsThumbNail();

            for (PostImages image : images) {
                newPost.addImages(image);
            }

            postImages = newPost
                    .getImages()
                    .stream()
                    .map(image -> new PostImageResponse(newPost.getId(), image.getImage_url(), image.getIsThumbnail()))
                    .collect(Collectors.toList());
        }


        // 로그인된 유저인지 토큰 확인 로직 추가 예정

        databasePostRepository.save(newPost);


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

        databasePostRepository.readPostBySomeone(post.getId());

        User author = post.getAuthor();

        String nickname = "(탈퇴한 사용자)";
        String profileImage = null;

        if (author != null && author.getDeletedAt() == null) {
            Optional<UserProfile> findProfile =
                    databaseUserProfileRepository.findProfileByUserId(author.getId());

            if (findProfile.isPresent()) {
                UserProfile profile = findProfile.get();
                nickname = profile.getNickname();
                profileImage = profile.getProfileImage();
            }
        }

        List<PostImageResponse> postImages = databasePostImageRepository.findPostImagesByPostId(post_id).stream().map(image -> new PostImageResponse(post_id, image.getImage_url(), image.getIsThumbnail())).collect(Collectors.toList());

        return new PostResponse(
                post.getId(),
                nickname,
                profileImage,
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
            List<PostImageResponse> images = post.getImages().stream().filter(image -> image.getDeletedAt() == null).map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

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
    public Boolean checkPostingByUser(Long post_id, Long user_id){
        Optional<Post> findPost = databasePostRepository.findPostByPostIdAndUserId(post_id, user_id);

        return findPost.isPresent();
    }

    @Override
    @Transactional
    public Page<PostResponse> getPostsOfPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Post> findPost = databasePostRepository.findPostsOfPage(pageable);
        // 권한 확인 로직 추가 예


        return findPost.map(post -> {

            User author = post.getAuthor();

            String nickname = "(탈퇴한 사용자)";
            String profileImage = null;

            if (author != null && author.getDeletedAt() == null) {
                Optional<UserProfile> findProfile =
                        databaseUserProfileRepository.findProfileByUserId(author.getId());

                if (findProfile.isPresent()) {
                    UserProfile profile = findProfile.get();
                    nickname = profile.getNickname();
                    profileImage = profile.getProfileImage();
                }
            }
            List<PostImageResponse> images = post.getImages().stream().filter(image -> image.getDeletedAt() == null).map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

            return new PostResponse(
                    post.getId(),
                    nickname,
                    profileImage,
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
        });
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long post_id, UpdatePostRequest req) throws IOException {
        Optional<Post> findPost = databasePostRepository.findPostById(post_id);
        if(findPost.isEmpty()){
            System.out.println("1");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Optional<User> findUser = databaseUserRepository.findUserById(req.getAuthor_id());

        if(findUser.isEmpty()){
            System.out.println("2");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }
        // 작성자만 수정 가능
        if(!findPost.get().getAuthor().getId().equals(req.getAuthor_id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_request");
        }

        Optional<UserProfile> findProfile = databaseUserProfileRepository.findProfileByUserId(req.getAuthor_id());


        if(findProfile.isEmpty()){
            System.out.println("3");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
        }

        Post data = findPost.get();

        UserProfile profile = findProfile.get();

        data.changeTitle(req.getTitle());
        data.changeContent(req.getContent());
        data.changePostType(req.getPostType());

        databasePostRepository.update(data.getId(), data.getTitle(), data.getContent(), data.getPostType());

        if (req.getNewImages() != null && !req.getNewImages().isEmpty()) {

            List<String> currentImageFiles = data.getImages().stream().map(PostImages::getImage_url).toList();

            for(String currentImageFile: currentImageFiles){
                File target = new File("/Users/ohsujin/KTB/Spring/spring-restapi/src/main/resources/static/upload/postImage/" + currentImageFile);
                if(target.exists()){
                    boolean deleted = target.delete();
                    if(!deleted){
                        System.out.println("삭제 실패");
                        throw new RuntimeException("파일 삭제 실패: " + currentImageFile);
                    }
                }
            }

            databasePostImageRepository.deleteAllPostImagesByPostId(data.getId());

            System.out.println("원래 이미지 삭제 완료");

            List<MultipartFile> newImageFiles = req.getNewImages();
            List<String> newImageFileNames = new ArrayList<>();
            for(MultipartFile newImageFile : newImageFiles){
                String fileName = newImageFile.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String extension = null;

                if (fileName != null) {
                    extension = fileName.substring(fileName.lastIndexOf("."));
                }

                String newFileName = uuid + extension;
                System.out.println("new " + newFileName);

                String filePath = "/Users/ohsujin/KTB/Spring/spring-restapi/src/main/resources/static/upload/postImage/" + newFileName;

                newImageFile.transferTo(new File(filePath));
                newImageFileNames.add(newFileName);
            }

            // 수정
            List<PostImages> images = newImageFileNames.stream().map(image -> PostImages.create(data, image, false)).toList();
            images.getFirst().changeIsThumbNail();

            for (PostImages image : images) {
                data.addImages(image);
            }
        }

        List<PostImageResponse> images = data.getImages().stream().filter(image -> image.getDeletedAt() == null).map(image -> new PostImageResponse(data.getId(), image.getImage_url(),image.getIsThumbnail())).collect(Collectors.toList());

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
        UserProfile profile = findProfile.get();

        List<PostImageResponse> postImages = post.getImages().stream().map(postImage -> new PostImageResponse(post_id, postImage.getImage_url(), postImage.getIsThumbnail())).toList();
        List<String> deletePostImageFiles = postImages.stream().map(PostImageResponse::getImage_url).toList();

        for(String deletePostImageFile : deletePostImageFiles){
            File target = new File("/Users/ohsujin/KTB/Spring/spring-restapi/src/main/resources/static/upload/postImage/" + deletePostImageFile);
            if(target.exists()){
                boolean deleted = target.delete();
                if(!deleted){
                    System.out.println("삭제 실패");
                    throw new RuntimeException("파일 삭제 실패: " + deletePostImageFile);
                }
            }
        }

        databasePostRepository.deletePostById(post_id);
        databasePostImageRepository.deleteAllPostImagesByPostId(post_id);


        databaseLikeRepository.deleteLikePostInfo(post_id);
        databaseCommentRepository.deleteCommentByPostId(post_id);

        return new PostResponse(post.getId(), profile.getNickname(), profile.getProfileImage(), post.getTitle(), post.getContent(), postImages, post.getPostType(), post.getWatch(), post.getLikeCount(), post.getCommentCount(), post.getCreatedAt(), post.getUpdatedAt());
    }

    // pageable
    // List
    public List<PostResponse> searchAsList(String keyword) {
        List<Post> posts = databasePostRepository.findByTitleContainingIgnoreCase(keyword);
        return posts.stream().map(post ->{
            Optional<UserProfile> profile = databaseUserProfileRepository.findProfileByUserId(post.getAuthor().getId());
            if(profile.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
            }

            UserProfile authorProfile = profile.get();
            List<PostImageResponse> images = post.getImages().stream().filter(image -> image.getDeletedAt() == null).map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

            return new PostResponse(post.getId(), authorProfile.getNickname(), authorProfile.getProfileImage(), post.getTitle(), post.getContent(), images, post.getPostType(), post.getWatch(), post.getLikeCount(), post.getCommentCount(), post.getCreatedAt(), post.getUpdatedAt());
        }).toList();
    }

    // Page
    public Page<PostResponse> searchAsPage(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = databasePostRepository.findByTitleContainingIgnoreCase(keyword, pageable);

        return posts.map(
                post -> {
                    Optional<UserProfile> profile = databaseUserProfileRepository.findProfileByUserId(post.getAuthor().getId());
                    if(profile.isEmpty()){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
                    }

                    UserProfile authorProfile = profile.get();
                    List<PostImageResponse> images = post.getImages().stream().filter(image -> image.getDeletedAt() == null).map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

                    return new PostResponse(post.getId(), authorProfile.getNickname(), authorProfile.getProfileImage(), post.getTitle(), post.getContent(), images, post.getPostType(), post.getWatch(), post.getLikeCount(), post.getCommentCount(), post.getCreatedAt(), post.getUpdatedAt());
                });
    }

    // Slice
    public Slice<PostResponse> searchAsSlice(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<Post> posts = databasePostRepository.findSliceByTitleContainingIgnoreCase(keyword, pageable);

        return posts.map(post -> {
            Optional<UserProfile> profile = databaseUserProfileRepository.findProfileByUserId(post.getAuthor().getId());
            if(profile.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found");
            }

            UserProfile authorProfile = profile.get();
            List<PostImageResponse> images = post.getImages().stream().filter(image -> image.getDeletedAt() == null).map(image -> new PostImageResponse(post.getId(), image.getImage_url(), image.getIsThumbnail())).toList();

            return new PostResponse(post.getId(), authorProfile.getNickname(), authorProfile.getProfileImage(), post.getTitle(), post.getContent(), images, post.getPostType(), post.getWatch(), post.getLikeCount(), post.getCommentCount(), post.getCreatedAt(), post.getUpdatedAt());
        });
    }

}
