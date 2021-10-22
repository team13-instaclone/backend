package com.team13.teamplay2insta.dto.post;

import com.team13.teamplay2insta.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MainResponseDto {
    private Long postId;
    private String username;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String content;

//    public MainResponseDto(Long postId,
//                               String username,
//                               String image,
//                               LocalDateTime createdAt,
//                               LocalDateTime modifiedAt,
//                               String content) {
//        this.postId = postId;
//        this.username = username;
//        this.image = image;
//        this.content = content;
//
//
//    }

}
