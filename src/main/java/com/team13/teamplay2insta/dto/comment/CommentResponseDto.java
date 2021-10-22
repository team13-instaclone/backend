package com.team13.teamplay2insta.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    Long postId;
    Long commentId;
    String username;
    String comment;
    LocalDateTime createdAt;
}
