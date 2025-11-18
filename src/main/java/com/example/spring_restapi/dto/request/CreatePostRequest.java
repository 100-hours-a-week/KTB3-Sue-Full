package com.example.spring_restapi.dto.request;

import com.example.spring_restapi.model.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@RequiredArgsConstructor
@Getter
public class CreatePostRequest {
    @Schema(description = "게시글 작성 - 작성자 아이디", example = "1L")
    private Long author_id;

    @Schema(description = "게시글 작성 - 제목", example = "TIL")
    private String title;

    @Schema(description = "게시글 작성 - 내용", example = "Today I learned...")
    private String content;

    @Schema(description = "게시글 작성 - 콘텐츠 이미지", example = "content images")
    private List<MultipartFile> images;

    @Schema(description = "게시글 작성 - 게시글 카테고리", example = "NOTICE")
    private PostType postType;

}
