package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DatabaseLikeRepository implements LikeRepository {

    @PersistenceContext
    EntityManager em;

    public DatabaseLikeRepository(){}

    @Override
    @Transactional
    public void save(Like like){
        em.persist(like);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Like> findLikesByPostId(Long post_id){
        TypedQuery<Like> query = em.createQuery("""
                select l
                from Like l
                where l.post.id = :post_id
                and l.deletedAt IS NULL
                """, Like.class);

        query.setParameter("post_id", post_id);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Like> findLikesByUserId(Long user_id) {
        TypedQuery<Like> query = em.createQuery("""
                select l
                from Like l
                where l.user.id = :user_id
                and l.deletedAt IS NULL
                """, Like.class);
        query.setParameter("user_id", user_id);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Like> findLikeOfPostByUserId(Long post_id, Long user_id) {
        List<Like> query = em.createQuery("""
                select l
                from Like l
                where l.user.id = :user_id and l.post.id = :post_id
                and l.deletedAt IS NULL
                """, Like.class)
        .setParameter("user_id", user_id)
        .setParameter("post_id", post_id).getResultList();

        return query.stream().findFirst();
    }

    @Override
    @Transactional
    public void deleteLike(Long id){
        Like deleteLike = em.find(Like.class, id);
        deleteLike.setDeletedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteLikeOfPostByUserId(Long post_id, Long user_id) {
        Like deleteLike = findLikeOfPostByUserId(post_id, user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not_found"));
        deleteLike.setDeletedAt(LocalDateTime.now());
;    }

    @Override
    @Transactional
    public List<Like> deleteLikePostInfo(Long post_id) {
        List<Like> deleteLikes = findLikesByPostId(post_id);
        deleteLikes.forEach(like -> like.setDeletedAt(LocalDateTime.now()));
        return deleteLikes;
    }

}
