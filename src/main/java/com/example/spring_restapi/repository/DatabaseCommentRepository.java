package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DatabaseCommentRepository implements CommentRepository{

    @PersistenceContext
    EntityManager em;

    public DatabaseCommentRepository(){}

    @Override
    @Transactional
    public void save(Comment comment){
        em.persist(comment);
    }

    @Override
    @Transactional
    public Optional<Comment> update(Comment comment){
        Comment updateComment = em.find(Comment.class, comment.getId());
        updateComment.changeContent(comment.getContent());
        updateComment.setUpdatedAt(LocalDateTime.now());

        return Optional.of(updateComment);
    }

    @Override
    @Transactional
    public Optional<Comment> deleteComment(Comment comment){
        Comment deleteComment = em.find(Comment.class, comment.getId());
        deleteComment.setDeletedAt(LocalDateTime.now());
        return Optional.of(deleteComment);
    }

    @Override
    @Transactional
    public List<Comment> deleteCommentByPostId(Long post_id){
        List<Comment> deleteComments = findCommentsByPosId(post_id);
        deleteComments.forEach(deleteCommnet -> deleteCommnet.setDeletedAt(LocalDateTime.now()));

        return deleteComments;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findCommentByCommentId(Long comment_id){
        TypedQuery<Comment> query = em.createQuery("""
                select c
                from Comment c
                where c.id = :id
                and c.deletedAt IS NULL
                """, Comment.class);

        query.setParameter("id", comment_id);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByPosId(Long post_id){
        TypedQuery<Comment> query = em.createQuery("""
                select c
                from Comment c
                where c.post.id = :id
                and c.deletedAt IS NULL
                order by c.createdAt
                """, Comment.class);

        query.setParameter("id", post_id);

        return query.getResultList();
    }
}
