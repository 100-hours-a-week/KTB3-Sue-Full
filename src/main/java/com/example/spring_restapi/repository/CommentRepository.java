package com.example.spring_restapi.repository;

import com.example.spring_restapi.dto.Comment;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CommentRepository {
    private final Map<Long, Comment> commentMap = new HashMap<>();
    private long sequence;

    public CommentRepository(){
        sequence = 0;

        Comment comment1 = new Comment(1L, 1L, 2L, "Cool~~", LocalDateTime.parse("2025-10-16T10:00:00"));
        Comment comment2 = new Comment(2L, 2L, 3L, "OTL...", LocalDateTime.parse("2025-10-16T14:00:00"));
        Comment comment3 = new Comment(3L, 2L, 1L, "OMG", LocalDateTime.parse("2025-10-17T10:00:00"));
        Comment comment4 = new Comment(4L, 3L, 1L, "Damn!", LocalDateTime.parse("2025-10-19T10:00:00"));

        sequence++;
        commentMap.put(comment1.getComment_id(), comment1);
        sequence++;
        commentMap.put(comment2.getComment_id(), comment2);
        sequence++;
        commentMap.put(comment3.getComment_id(), comment3);
        sequence++;
        commentMap.put(comment4.getComment_id(), comment4);
    }

    public Comment save(Comment comment){
        sequence++;
        if(Optional.ofNullable(comment.getComment_id()).isEmpty()){
            comment.setComment_id(sequence);
        }
        commentMap.put(comment.getComment_id(), comment);
        return commentMap.get(comment.getComment_id());
    }

    public Optional<Comment> update(Comment comment){
        for(Map.Entry<Long, Comment> entry : commentMap.entrySet()){
            Comment find = entry.getValue();
            if(find.getComment_id().equals(comment.getComment_id())){
                return Optional.ofNullable(commentMap.put(comment.getComment_id(), comment));
            }
        }
        return Optional.empty();
    }

    public Comment deleteComment(Comment comment){
        return commentMap.remove(comment.getComment_id());
    }

    public Optional<Comment> findCommentByCommentId(Long comment_id){
        return Optional.ofNullable(commentMap.get(comment_id));
    }

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
