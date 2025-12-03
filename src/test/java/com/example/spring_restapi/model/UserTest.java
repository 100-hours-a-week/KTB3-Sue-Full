package com.example.spring_restapi.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class UserTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Rollback(false)
    void idTest(){
        User user = new User("osj1405@naver.com", "osj1405", "osj1405Confirm", UserRole.ROLE_USER);
        entityManager.persist(user);
    }

    @Test
    @Rollback(false)
    void createdUpdatedAtTest() {
        User user = new User("tester@adapterz.kr", "123aS!", "123aSConfirm", UserRole.ROLE_USER);
        LocalDateTime now = LocalDateTime.now();

        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        entityManager.persist(user);
    }

    @Test
    @Rollback(false)
    void enumeratedTest() {
        User user = new User("tester@adapterz.kr", "123aS!", "123aSConfirm", UserRole.ADMIN);
        entityManager.persist(user);
    }

    @Test
    @Rollback(false)
    void transientTest() {
        User user = new User("tester@adapterz.kr", "123aS!", "123aS!Confirm", UserRole.ADMIN);
        entityManager.persist(user);
    }

    @Test
    @Rollback(false)
    void flushTest() {
        User user = new User("duckjin1405@naver.com", "sujin1405", "sujin1405Confirm", UserRole.ROLE_USER);

        System.out.println("=== Flush (아무것도 없음) ===");
        entityManager.flush(); // 아무 것도 없음 (정상)
        System.out.println("==============");

        System.out.println("=== Persist ===");
        entityManager.persist(user);     // 영속화 (아직 INSERT 미발행)
        System.out.println("==============");

        System.out.println("=== Flush (INSERT 발생) ===");
        entityManager.flush();           // 여기서 INSERT 발생
        System.out.println("==============");
    }

    @Test
    @Rollback(false)
    void removeTest() {
        User user = new User("tester@adapterz.kr", "123aS!", "DeleteUser", UserRole.ROLE_USER);
        entityManager.persist(user);

        entityManager.flush(); // INSERT 실행
        System.out.println("=== INSERT 쿼리 실행됨 ===");

        entityManager.remove(user);
        System.out.println("=== remove 호출 (아직 DELETE 쿼리 안 나감) ===");

        entityManager.flush();
        System.out.println("=== DELETE 쿼리 실행됨 ===");
    }

    @Test
    @Rollback(false)
    void clearTest() {
        User user = new User("clear@adapterz.kr", "123aS!", "ClearUser", UserRole.ROLE_USER);

        System.out.println("=== Persist ===");
        entityManager.persist(user); // 영속화 (아직 INSERT 미발행)
        System.out.println("==============");

        System.out.println("=== Flush (INSERT 발생) ===");
        entityManager.flush(); // INSERT 실행
        System.out.println("==============");

        System.out.println("=== Clear (영속성 컨텍스트 초기화) ===");
        entityManager.clear(); // 영속성 컨텍스트 비움 → user는 준영속 상태
        System.out.println("==============");

        System.out.println("=== 1차 캐시 확인용 find() 호출 ===");
        User found = entityManager.find(User.class, user.getId());
        System.out.println("조회된 사용자 이메일: " + found.getEmail());
        System.out.println("==============");
    }

    @Test
    @Rollback(false)
    void detachTest() {
        for (int i = 1; i <= 5; i++) {
            User user = new User(
                    "tester" + i + "@adapterz.kr",
                    "123aS!" + i,
                    "Adapterz" + i,
                    UserRole.ROLE_USER
            );
            entityManager.persist(user);
        }
        entityManager.flush();
        System.out.println("=== INSERT 5명 실행됨 ===");

        // 두 번째 유저 detach
        User detachUser = entityManager.find(User.class, 16L);
        entityManager.detach(detachUser);
        System.out.println("=== 두 번째 유저 detach 됨 ===");

        // detach 된 유저 정보 수정
        detachUser.setUpdatedAt(LocalDateTime.now());
        entityManager.flush();
        System.out.println("=== flush 호출됨 ===");

        // DB에서 확인
        User findUser = entityManager.find(User.class, detachUser.getId());
        System.out.println("findUser.getUpdatedAt() : " + findUser.getUpdatedAt());
    }

    @Test
    @Rollback(false)
    void mergeAfterDetachUpdatesDB() {
        // 저장
        User user = new User("mergeTest@adapterz.kr", "123aS!", "BeforeMerge", UserRole.ROLE_USER);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear(); // 영속성 컨텍스트 비우기

        // 준영속 엔티티 수정
        user.setUpdatedAt(LocalDateTime.now());
        System.out.println("=== flush, clear 후 엔티티 수정 (DB 반영 X) ===");

        // merge 실행
        User managed = entityManager.merge(user);
        System.out.println("=== merge 실행 → 새로운 영속 엔티티 반환 ===");

        // 두 객체 비교
        System.out.println("user 객체 hashCode = " + System.identityHashCode(user));
        System.out.println("managed 객체 hashCode = " + System.identityHashCode(managed));
        System.out.println("user == managed : " + (user == managed));

        // flush로 DB 반영
        entityManager.flush();
        System.out.println("=== flush 실행 → UPDATE 쿼리 발생 ===");

        // 확인
        User findUser = entityManager.find(User.class, managed.getId());
        System.out.println("nickname = " + findUser.getUpdatedAt()); // After Merge
    }

//    @Test
//    @Rollback(false)
//    void oneToOneTest() {
//        // 프로필 생성
//        UserProfile profile = new UserProfile("sue", "image.png", "hi", "F");
//        profile.changeProfileImage("/images/tester.png");
//
//        // 유저 생성
//        User user = new User("ProfileOneToOnetester2@adapterz.kr", "123aS!", "tester", UserRole.ADMIN);
//
//        // 연관관계 설정 (User → UserProfile)
//        user.setProfile(profile);
//
//        // 저장
//        entityManager.persist(profile); // Profile 먼저 저장
//        entityManager.persist(user);    // User 저장
//
//        entityManager.flush();
//        entityManager.clear();
//
//        // 다시 조회
//        User findUser = entityManager.find(User.class, user.getId());
//        System.out.println("조회된 유저 이메일 = " + findUser.getEmail());
//        System.out.println("유저 프로필 이미지 경로 = " + findUser.getProfile().getProfile_image());
//
//        UserProfile findProfile = entityManager.find(UserProfile.class, profile.getId());
//        System.out.println("조회된 프로필 id = " + findProfile.getId());
//        System.out.println("프로필에 연결된 유저 이메일 = " + findProfile.getUser().getEmail());
//    }
}
