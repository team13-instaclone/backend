package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.dto.CommentRequestDto;
import com.team13.teamplay2insta.dto.CommentResponseDto;
import com.team13.teamplay2insta.dto.ResponseDto;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.exception.defaultResponse.DefaultResponse;
import com.team13.teamplay2insta.exception.defaultResponse.StatusCode;
import com.team13.teamplay2insta.exception.defaultResponse.SuccessYn;
import com.team13.teamplay2insta.model.Comment;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import com.team13.teamplay2insta.service.CommentService;
import com.team13.teamplay2insta.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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

//    @ApiOperation(value = "댓글 수정")
//    @PutMapping("/comments/{commentId}")
//    public void modifyComment(
//            @PathVariable @ApiParam(value = "댓글 아이디", required = true) Long commentId
//            , @RequestBody @ApiParam(value = "댓글 한개의 정보를 가진 객체", required = true) CommentRequestDto commentRequestDto
//            , @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails
//    ) {
//        commentService.modifyComment(commentId, commentRequestDto, userDetails.getUser());
//    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/api/comment")
    public void deleteComment(
            @PathVariable @ApiParam(value = "댓글 아이디", required = true) Long commentId
            , @RequestParam @ApiParam(value = "댓글 한개의 정보를 가진 객체", required = true) Long articleId
            , @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(articleId, commentId, userDetails.getUser());
    }

    private void checkLogin(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
    }
}