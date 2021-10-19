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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.team13.teamplay2insta.dto.PostRequestDto;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    // 상세페이지 보여주기
    @PostMapping("/api/detail")
    public List<Post> detailGet() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    // 상세페이지 수정
    @PutMapping("/api/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.update(id, postRequestDto);
        return id;
    }

    // 상세페이지 삭제
    @DeleteMapping("/api/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
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

    @DeleteMapping("/api/post")
    public ResponseDto deletePost(Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
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