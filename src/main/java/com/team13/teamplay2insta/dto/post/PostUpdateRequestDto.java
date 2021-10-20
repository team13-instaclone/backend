package com.team13.teamplay2insta.dto.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PostUpdateRequestDto {
    private Long postid;

    @NotBlank
    private String username;

    @NotBlank
    private String content;

    private MultipartFile image;
}
