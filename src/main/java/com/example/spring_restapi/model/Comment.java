package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {
    @Schema(description = "댓글 아이디", example = "2L")
    private Long id;

    @Schema(description = "게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "작성자 아이디", example = "3L")
    private Long author_id;

    @Schema(description = "댓글 내용", example = "cool...")
    private String content;

    @Schema(description = "댓글 작성일자", example = "20251020T10:00:00")
    private LocalDateTime write_date;

    @Schema(description = "댓글 수정일자", example = "20251022T10:00:00")
    private LocalDateTime update_date;

    protected Comment() {}

    public Comment(Long post_id, Long author_id, String content, LocalDateTime write_date, LocalDateTime update_date){
        this.post_id = post_id;
        this.author_id = author_id;
        this.content = content;
        this.write_date = write_date;
        this.update_date = update_date;
    }

}
