package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.awsS3.S3Uploader;
import com.team13.teamplay2insta.dto.PostUploadDto;
import com.team13.teamplay2insta.dto.ResponseDto;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import com.team13.teamplay2insta.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;

    //로컬에 저장하는 테스트용 api입니다.
    @PostMapping("/api/postToLocal")
    public ResponseDto uploadToLocal(PostUploadDto uploadDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.uploadPostToLocal(uploadDto,userDetails);

        return new ResponseDto("success","저장됨");
    }

    @PostMapping("/api/post")
    public ResponseDto upload(PostUploadDto uploadDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        checkLogin(userDetails);
        String imageUrl = postService.uploadPost(uploadDto,userDetails);
        if(imageUrl == null) return new ResponseDto("failed","이미지 업로드에 실패하였습니다");

        return new ResponseDto("success","저장됨");
    }

    @DeleteMapping("/api/post")
    public ResponseDto deletePost(@RequestParam Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        postService.deletePost(postid);
        return new ResponseDto("success","삭제완료");
    }


    private void checkLogin(UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new CustomErrorException("로그인 사용자만 이용가능합니다.");
        }
    }

}
