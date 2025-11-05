package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.PostImages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DatabasePostImagesRepository implements PostImagesRepository{

    @PersistenceContext
    EntityManager em;
    public DatabasePostImagesRepository(){}

    @Override
    @Transactional
    public void save(PostImages postImages) {
        em.persist(postImages);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostImages> findPostImagesByPostId(Long post_id) {
        TypedQuery<PostImages> query = em.createQuery("""
                select i
                from PostImages i
                where i.post.id = :post_id
                and i.deletedAt IS NULL
                """, PostImages.class);

        query.setParameter("post_id", post_id);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostImages> findAllThumbnail() {
        TypedQuery<PostImages> query = em.createQuery("""
                select i
                from PostImages i
                where i.isThumbnail = TRUE
                and i.deletedAt IS NULL
                """, PostImages.class);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostImages> findThumbnailByPostId(Long post_id) {
        TypedQuery<PostImages> query = em.createQuery("""
                select i
                from PostImages i
                where i.isThumbnail = TRUE
                and i.post.id = :post_id
                and i.deletedAt IS NULL
                """, PostImages.class);

        query.setParameter("post_id", post_id);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public PostImages deletePostImagesById(Long id) {
        PostImages deleteImage = em.find(PostImages.class, id);
        deleteImage.setDeletedAt(LocalDateTime.now());
        return deleteImage;
    }

    @Override
    @Transactional
    public List<PostImages> deleteAllPostImagesByPostId(Long post_id) {
        List<PostImages> deleteImages = findPostImagesByPostId(post_id);

        deleteImages.forEach(image -> image.setDeletedAt(LocalDateTime.now()));
        return deleteImages;
    }

}
