package com.example.spring_restapi.repository;

import com.example.spring_restapi.model.Comment;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DatabaseCommentRepository implements CommentRepository{
    private final ConcurrentHashMap<Long, Comment> commentMap = new ConcurrentHashMap<>();
    private long sequence;

    public DatabaseCommentRepository(){
        sequence = 0;

        Comment comment1 = new Comment(null, 1L, 2L, "Cool~~", LocalDateTime.parse("2025-10-16T10:00:00"));
        Comment comment2 = new Comment(null, 2L, 3L, "OTL...", LocalDateTime.parse("2025-10-16T14:00:00"));
        Comment comment3 = new Comment(null, 2L, 1L, "OMG", LocalDateTime.parse("2025-10-17T10:00:00"));
        Comment comment4 = new Comment(null, 3L, 1L, "Damn!", LocalDateTime.parse("2025-10-19T10:00:00"));

        save(comment1);
        save(comment2);
        save(comment3);
        save(comment4);
    }

    @Override
    public Comment save(Comment comment){
        sequence++;
        if(Optional.ofNullable(comment.getComment_id()).isEmpty()){
            comment.setComment_id(sequence);
        }
        commentMap.put(comment.getComment_id(), comment);
        return commentMap.get(comment.getComment_id());
    }

    @Override
    public Optional<Comment> update(Comment comment){
        return Optional.ofNullable(commentMap.put(comment.getComment_id(), comment));
    }

    @Override
    public Comment deleteComment(Comment comment){
        return commentMap.remove(comment.getComment_id());
    }

    @Override
    public Optional<Comment> findCommentByCommentId(Long comment_id){
        return Optional.ofNullable(commentMap.get(comment_id));
    }

    @Override
    public List<Comment> findCommentByPosId(Long post_id){
        List<Comment> findComment = new ArrayList<>();
        for(Map.Entry<Long, Comment> entry : commentMap.entrySet()){
            Comment find = entry.getValue();
            if(find.getPost_id().equals(post_id)){
                findComment.add(find);
            }
        }
        return findComment;
    }
}
