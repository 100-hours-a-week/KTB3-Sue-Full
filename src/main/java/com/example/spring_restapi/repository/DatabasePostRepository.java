package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DatabasePostRepository implements PostRepository {

    @PersistenceContext
    EntityManager em;

    public DatabasePostRepository(){
    }

    @Override
    @Transactional
    public void save(Post post){
        em.persist(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPost(){
        TypedQuery<Post> query = em.createQuery("""
                select p
                from Post p
                where p.deletedAt IS NULL
                """, Post.class);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findPostsOfPage(int page, int size){
        TypedQuery<Post> query = em.createQuery("""
                select p
                from Post p
                join fetch p.author
                where p.deletedAt IS NULL
                order by p.createdAt
                """, Post.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findPostByPostId(Long post_id){
        TypedQuery<Post> query = em.createQuery("""
                select p
                from Post p
                join fetch p.author
                where p.id = :id
                AND p.deletedAt IS NULL
                """, Post.class);
        query.setParameter("id", post_id);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findPostByPostAuthorId(Long author_id){
        TypedQuery<Post> query = em.createQuery("""
                select p
                from Post p
                join fetch p.author
                where p.author.id = :id
                and p.deletedAt IS NULL
                """, Post.class);
        query.setParameter("id", author_id);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Post post) {
        Post updatePost = em.find(Post.class, post.getId());

        updatePost.changeTitle(post.getTitle());
        updatePost.changeContent(post.getContent());
        updatePost.changePostType(post.getPostType());
        updatePost.setUpdatedAt(LocalDateTime.now());

    }

    @Override
    @Transactional
    public void readPostBySomeone(Post post){
        Post readPost = em.find(Post.class, post.getId());
        readPost.increaseWatch();
    }

    @Override
    @Transactional
    public void likeBySomeone(Long post_id) {
        Post readPost = em.find(Post.class, post_id);
        readPost.increaseLikeCount();
    }

    @Override
    @Transactional
    public void unlikeBySomeone(Long post_id) {
        Post readPost = em.find(Post.class, post_id);
        readPost.decreaseLikeCount();
    }

    @Override
    @Transactional
    public void writeCommentBySomeone(Post post){
        Post readPost = em.find(Post.class, post.getId());
        readPost.increaseCommentCount();
    }

    @Override
    @Transactional
    public void deleteCommentBySomeone(Post post){
        Post readPost = em.find(Post.class, post.getId());
        readPost.decreaseCommentCount();
    }

    @Override
    @Transactional
    public void deletePostByPostId(Long post_id){
        Post deletePost = em.find(Post.class, post_id);
        deletePost.setDeletedAt(LocalDateTime.now());
    }
}
