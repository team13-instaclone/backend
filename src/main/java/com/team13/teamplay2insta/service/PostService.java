package com.team13.teamplay2insta.service;


import com.team13.teamplay2insta.dto.PostRequestDto;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 상세페이지 수정하기
    @Transactional
    public Long update(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        post.update(postRequestDto);
        return post.getId();
    }

    // 게시글 조회
//    public List<Post> postList = postRepository.getPostList(){
//        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
//        for (Post post : postList) {
//            PostResponseDto newPostDto = new PostReponseDto();
//            postReseponseDtoList.add(newPostDto);
//        }
//        return postResponseDtoList;
//    }

    // 게시글 작성
//    @Transactional
//    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails){
//        User user = userDetails.getUser();
//        Post post = new Post(postRequestDto, user);
//        postRepository.save(post);
//    }

}
