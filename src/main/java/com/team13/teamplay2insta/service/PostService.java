package com.team13.teamplay2insta.service;

import com.team13.teamplay2insta.dto.PostRequestDto;
import com.team13.teamplay2insta.dto.PostResponseDto;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 조회
    public List<Post> postList = postRepository.getPostList(){
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostResponseDto newPostDto = new PostReponseDto();
            postReseponseDtoList.add(newPostDto);
        }
        return postResponseDtoList;
    }

    // 게시글 작성
    @Transactional
    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

}
