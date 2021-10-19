package com.team13.teamplay2insta.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostUploadDto {
    private String username;
    private String content;
    private MultipartFile file;
}
