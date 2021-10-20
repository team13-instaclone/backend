package com.team13.teamplay2insta.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PostSaveDto {
    private String username;
    private String content;
    private String imageUrl;
}
