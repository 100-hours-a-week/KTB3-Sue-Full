# Focus Place ☕️
> **집중하기 좋은 공간들을 기록하고 공유하는 아카이빙 서비스 – Focus Place의 REST API 서버**

카공하기 좋은 카페, 조용한 스터디 카페, 도서관, 코워킹 스페이스까지  
“언제 어디서 집중 잘 됐는지”를 기록하고, 다른 사람들과 공유할 수 있는 서비스입니다.

해당 Repo는 **Spring Boot 기반의 백엔드 API 서버**로,  
회원가입, 로그인, 장소(게시글) CRUD, 이미지 업로드, 좋아요, 댓글 기능을 제공합니다.


Sue의 Fullstack 프로젝트입니다.

## 🚀 Tech Stack

- **Language**: Java (JDK 17+)
- **Framework**: Spring Boot  
- **Modules**
  - Spring Web
  - Spring Data JPA
  - Spring Security + JWT
- **Database**: MySQL
- **Build Tool**: Gradle
- **ETC**
  - Swagger / Springdoc(OpenAPI) – API 문서
  - (Local Storage ->)S3 – 이미지 저장

## 주요 기능
- 회원 가입, 회원정보 수정, 회원 탈퇴
- 게시물 CRUD
- 댓글 CRUD
- 좋아요 CRD
- 이미지 처리(프로필 이미지, 게시글 이미지)
- 스프링 시큐리티 JWT Access Token

* 모든 Delete는 soft delete

## Storage
AWS S3

## Security
LoginFilter, JWTFilter 구현


## 프로젝트 폴더 구조
```
📦src
 ┣ 📂config
 ┃ ┣ 📜 AuditingConfig
 ┃ ┣ 📜 SecurityConfig
 ┃ ┗ 📜 S3Config
 ┣ 📂controller
 ┃ ┣ 📜 UserController
 ┃ ┣ 📜 PostController
 ┃ ┣ 📜 CommentController
 ┃ ┗ 📜 LikeController
 ┗ 📂service
 ┃ ┣ 📜 UserService (Interface)
 ┃ ┃ ┗ 📜 UserServiceImpl
 ┃ ┣ 📜 UpdateUserService (Interface)
 ┃ ┃ ┣ 📜 UpdateUserInfoImpl
 ┃ ┃ ┣ 📜 UpdateUserPasswordImpl
 ┃ ┃ ┗ 📜 UpdateUserNicknameImpl
 ┃ ┣ 📜 PostService (Interface)
 ┃ ┃ ┗ 📜 PostServiceImpl
 ┃ ┣ 📜 CommentService (Interface)
 ┃ ┃ ┗ 📜 CommentServiceImpl
 ┃ ┣ 📜 LikeService (Interface)
 ┃ ┃ ┗ 📜 LikeServiceImpl
 ┃ ┗ 📜 UserDetailServiceImpl
 ┗ 📂repository
 ┃ ┣ 📜 UserRepository
 ┃ ┣ 📜 UserProfileRepository
 ┃ ┣ 📜 PostRepository
 ┃ ┣ 📜 PostImagesRepository
 ┃ ┣ 📜 CommentRepository
 ┃ ┗ 📜 LikeRepository
 ┗ 📂model
 ┃ ┣ 📜 User
 ┃ ┣ 📜 UserProfile
 ┃ ┣ 📜 Post
 ┃ ┣ 📜 PostImages
 ┃ ┣ 📜 Comment
 ┃ ┣ 📜 Like
 ┃ ┗ 📜 AbstractAuditable
 ┗ 📂dto
 ┃ ┣ 📂auth
 ┃ ┃ ┗ 📜 CustomUserDetails
 ┃ ┣ 📂request
 ┃ ┃ ┗ 📜 SignUpRequest
 ┃ ┃ ┗ 📜 EmailCheckRequest
 ┃ ┃ ┗ 📜 NicknameCheckRequest
 ┃ ┃ ┗ 📜 UserIdBodyRequest
 ┃ ┃ ┗ 📜 EmailCheckRequest
 ┃ ┃ ┗ 📜 UpdateUserInfoRequest
 ┃ ┃ ┗ 📜 EmailCheckRequest
 ┃ ┃ ┗ 📜 CreatePostRequest
 ┃ ┃ ┗ 📜 UpdatePostRequest
 ┃ ┃ ┗ 📜 DeletePostRequest
 ┃ ┃ ┗ 📜 CreateCommentRequest
 ┃ ┃ ┗ 📜 UpdateCommentRequest
 ┃ ┃ ┗ 📜 CheckLikedByUserRequest
 ┃ ┗ 📂response
 ┃ ┃ ┗ 📜 CommonResponse
 ┃ ┃ ┗ 📜 LoginResponse
 ┃ ┃ ┗ 📜 UserResponse
 ┃ ┃ ┗ 📜 UserProfileResponse
 ┃ ┃ ┗ 📜 UserInfoResponse
 ┃ ┃ ┗ 📜 PostResponse
 ┃ ┃ ┗ 📜 PostImageResponse
 ┃ ┃ ┗ 📜 CommentResponse
 ┃ ┃ ┗ 📜 LikeResponse
 ┃ ┃ ┗ 📜 LikeListResponse
 ┃ ┗ 📂error
 ┃ ┃ ┗ 📜 GlobalExceptionHandler
 ┃ ┗ 📂storage
 ┃   ┗ 📜 S3Uploader

```

### N+1 문제 개선
게시글 조회 시 작성한 유저의 정보를 가져오는 쿼리가 한 번 더 발생하는 N+1 문제 발생
-> fetch join을 적용하여 개선

게시글 조회 시 이미지 불러오려면 findPostImagesByPostId를 통해 쿼리가 한 번 더 실행되어야 함
-> BatchSize를 적용함

### Spring Security 트러블 슈팅
user.getUserRole()은 UserRole을 반환한다. UserRole -> USER || ADMIN
ROLE_ 접두사가 없어 유저의 role을 제대로 확인하지 못해 403 에러 발생
로그를 통하여 rawRole을 확인 후, 불일치를 제거하여 해결


