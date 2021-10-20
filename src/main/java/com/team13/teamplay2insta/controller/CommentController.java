package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.dto.comment.CommentDeleteRequestDto;
import com.team13.teamplay2insta.dto.comment.CommentRequestDto;
import com.team13.teamplay2insta.dto.comment.CommentResponseDto;
import com.team13.teamplay2insta.dto.post.ResponseDto;
import com.team13.teamplay2insta.dto.comment.CommentUpdateRequestDto;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.model.Comment;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import com.team13.teamplay2insta.service.CommentService;
import com.team13.teamplay2insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/api/comment")
    public ResponseDto addComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userService.userFromUserDetails(userDetails);
        System.out.println("CommentController 댓글작성:"+userDetails);
        checkLogin(userDetails);
        Long postId = commentRequestDto.getPostid();
        User user = userDetails.getUser();
        Comment comment = commentService.addComment(postId, commentRequestDto, user);

        CommentResponseDto responseDto = new CommentResponseDto(
                comment.getPost().getId(),
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );

        return new ResponseDto("success",responseDto);
    }

    @PutMapping("/api/comment")
    public ResponseDto modifyComment(
            @RequestBody CommentUpdateRequestDto requestDto,
            @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails
    ) {
        checkLogin(userDetails);
        return commentService.modifyComment(
                requestDto.getCommentid(),
                requestDto.getComment(),
                userDetails.getUser());
    }

    @DeleteMapping("/api/comment")
    public ResponseDto deleteComment(
            @RequestBody CommentDeleteRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        checkLogin(userDetails);
        commentService.deleteComment(requestDto.getCommentid(), userDetails.getUser());

        return new ResponseDto("success", "댓글이 삭제되었습니다");
    }

    private void checkLogin(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
    }
}