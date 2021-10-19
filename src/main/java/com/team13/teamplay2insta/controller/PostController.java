package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.dto.PostRequestDto;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    // 상세페이지 보여주기
    @PostMapping("/api/detail")
    public List<Post> detailGet() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

//    // 상세페이지 수정
//    @PutMapping("/api/post/{id}")
//    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
//        postService.update(id, postRequestDto);
//        return id;
//    }
//
//    // 상세페이지 삭제
//    @DeleteMapping("/api/post/{id}")
//    public Long deletePost(@PathVariable Long id) {
//        postRepository.deleteById(id);
//        return id;
//    }

}
