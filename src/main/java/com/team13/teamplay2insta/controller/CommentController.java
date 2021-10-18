package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.dto.CommentRequestDto;
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
@Api(tags = "Comment Controller Api V1")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @ApiOperation(value = "댓글 추가")
    @PostMapping("/comments")
    public List<Comment> addComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) {
        User user = userService.userFromUserDetails(userDetails);
        Long postId = commentRequestDto.getPostid();
        Comment comment = commentService.addComment(postId, commentRequestDto, user);
        return (List<Comment>) ResponseEntity.ok(DefaultResponse.res(SuccessYn.OK, StatusCode.OK, "댓글 추가가 완료되었습니다.", comment));
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
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
            @PathVariable @ApiParam(value = "댓글 아이디", required = true) Long commentId
            , @RequestParam @ApiParam(value = "댓글 한개의 정보를 가진 객체", required = true) Long articleId
            , @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(articleId, commentId, userDetails.getUser());
    }
}