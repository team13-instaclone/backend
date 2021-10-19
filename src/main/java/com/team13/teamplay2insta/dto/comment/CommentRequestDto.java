package com.team13.teamplay2insta.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long postid;
    private Long commentid;
    private String username;
    private String comment;
}