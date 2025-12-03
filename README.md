# Focus Place ☕️🧠
> **집중하기 좋은 공간들을 기록하고 공유하는 아카이빙 서비스 – Focus Place의 REST API 서버**

카공하기 좋은 카페, 조용한 스터디 카페, 도서관, 코워킹 스페이스까지  
“언제 어디서 집중 잘 됐는지”를 기록하고, 다른 사람들과 공유할 수 있는 서비스입니다.

해당 Repo는 **Spring Boot 기반의 백엔드 API 서버**로,  
회원가입, 로그인, 장소(게시글) CRUD, 이미지 업로드, 좋아요, 댓글 기능을 제공합니다.

## 참여자

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
  - S3 – 이미지 저장

## 주요 기능
- 회원 가입, 회원정보 수정, 회원 탈퇴
- 게시물 CRUD
- 댓글 CRUD
- 좋아요 CRD
- 이미지 처리(프로필 이미지, 게시글 이미지)
- 스프링 시큐리티 JWT Access Token

## Storage
AWS S3

## Security
LoginFilter, JWTFilter 구현


## 프로젝트 폴더 구조


