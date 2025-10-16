package com.example.spring_restapi.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {
    private Long post_id;
    private Long author_id;
    private String title;
    private String content;
    private List<String> images;
    private Set<Long> likedUserIds = new HashSet<>();
    private Integer watch;
    private LocalDateTime date;
    private LocalDateTime rewriteDate;

    public Post(){}

    public Post(Long post_id, Long author_id, String title, String content, List<String> images, Set<Long> likedUserIds, Integer watch, LocalDateTime date){
        this.post_id = post_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.images = images;
        this.likedUserIds = likedUserIds;
        this.watch = watch;
        this.date = date;
    }
    // Setter
    public void setPost_id(Long post_id){
        this.post_id = post_id;
    }

    public void setAuthor_id(Long author_id){
        this.author_id = author_id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setImages(List<String> images){
        this.images = images;
    }

    public void setLike(Set<Long> likedUserIds){
        this.likedUserIds = likedUserIds;
    }

    public void like(Long likeUserId) { likedUserIds.add(likeUserId); }

    public void unlike(Long unlikeUserId) { likedUserIds.remove(unlikeUserId); }

    public void setWatch(Integer watch){
        this.watch = watch;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public void setRewriteDate(LocalDateTime rewriteDate){
        this.rewriteDate = rewriteDate;
    }

    // Getter
    public Long getPost_id(){ return post_id; }

    public Long getAuthor_id() { return author_id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public List<String> getImages() { return images; }

    public Set<Long> getLikedUserIds() { return likedUserIds; }

    public Integer getLikeCount() { return likedUserIds.size(); }

    public Boolean isLikedByUser(Long user_id) { return likedUserIds.contains(user_id); }

    public Integer getWatch() { return watch; }

    public LocalDateTime getDate() { return date; }
}
