package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Post;
import com.example.spring_restapi.model.PostImages;
import com.example.spring_restapi.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            select p
            from Post p
            join fetch p.author
            where p.deletedAt IS NULL
            order by p.createdAt
            """)
    Page<Post> findPostsOfPage(Pageable pageable);

    @Query("""
            select p
            from Post p
            join fetch p.author
            where p.id = :post_id
                and p.deletedAt IS NULL
            """)
    Optional<Post> findPostById(Long post_id);

    @Query("""
            select p
            from Post p
            join fetch p.author
            where p.author.id = :author_id
            and p.deletedAt IS NULL
            """)
    List<Post> findPostByPostAuthor_Id(Long author_id);

    // 제목에 키워드가 포함된 게시글들 검색 (대소문자 무시, 전체 결과 반환)
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    // 제목에 키워드가 포함된 게시글들 검색 (대소문자 무시, 페이징/정렬 포함 + 전체 건수까지 조회)
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // 제목에 키워드가 포함된 게시글들 검색 (대소문자 무시, 페이징/정렬 포함 + 다음 페이지 존재 여부만 확인, total count 쿼리 X)
    Slice<Post> findSliceByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    @Modifying
    @Query("""
            update Post p
            set p.title = :title,
                p.content= :content,
                p.postType = :postType
            where p.id = :id
            """)
    void update(Long id, String title, String content, PostType postType);

    @Modifying
    @Query("""
            update Post p
            set p.watch = p.watch + 1
            where p.id = :id
            """)
    void readPostBySomeone(Long id);

    @Modifying
    @Query("""
            update Post p
            set p.likeCount = p.likeCount + 1
            where p.id = :post_id
            """)
    void increaseLikeCount(Long post_id);

    @Modifying
    @Query("""
            update Post p
            set p.likeCount = p.likeCount - 1
            where p.id = :post_id
            """)
    void unlikeBySomeone(Long post_id);

    @Modifying
    @Query("""
            update Post p
            set p.commentCount = p.commentCount + 1
            where p.id = :post_id
            """)
    void writeCommentBySomeone(Long post_id);

    @Modifying
    @Query("""
            update Post p
            set p.commentCount = p.commentCount - 1
            where p.id = :id
            """)
    void deleteCommentBySomeone(Post post);

    @Modifying
    @Query("""
            update Post p
            set p.deletedAt = CURRENT_TIMESTAMP
            where p.id = :post_id
            """)
    void deletePostById(Long post_id);

}
