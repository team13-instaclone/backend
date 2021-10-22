package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.awsS3.S3Uploader;
import com.team13.teamplay2insta.dto.post.*;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import com.team13.teamplay2insta.service.PostService;
import jdk.jfr.internal.tool.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;

    //메인페이지 게시글 조회`
//    @GetMapping("/api/main")
//    public List<Post> getPost(){
//
//        return postRepository.findAllByOrderByCreatedAtDesc();
//    }
    //메인페이지 게시글 조회`
    @GetMapping("/api/main")
    public ResponseDto getPost(){
         List<MainResponseDto> data = postService.getAllContents() ;

        return new ResponseDto("success", data);

    }

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

    @PutMapping("/api/post")
    public ResponseDto update(PostUpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        checkLogin(userDetails);
        PostUpdateResponseDto postUpdateResponseDto = postService.updatePost(updateRequestDto,userDetails);

        return new ResponseDto("success",postUpdateResponseDto);
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