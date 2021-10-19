package com.team13.teamplay2insta.service;

import com.team13.teamplay2insta.awsS3.S3Uploader;
import com.team13.teamplay2insta.dto.PostSaveDto;
import com.team13.teamplay2insta.dto.PostUploadDto;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.team13.teamplay2insta.dto.PostRequestDto;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;

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
//ec2에 사진을 저장하고자 한다면 이렇게 하면 되긴 함.
    @Value("${file.path}")
    private String uploadFolder;
    //로컬에 저장하는 연습용 api를 위한 메서드입니다.
    public void uploadPostToLocal(PostUploadDto postUploadDto, UserDetailsImpl userDetails){
        UUID uuid = UUID.randomUUID();//고유성이 보장됨
        String imageFileName = uuid+postUploadDto.getFile().getOriginalFilename(); //photo.jpg

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);
        try{
            Files.write(imageFilePath, postUploadDto.getFile().getBytes());
            System.out.println(uploadFolder);
        }catch (Exception e){
            throw new CustomErrorException("파일저장 실패");
        }
    }


    public String uploadPost(PostUploadDto uploadDto, UserDetailsImpl userDetails) throws IOException {
        String imageUrl = s3Uploader.upload(uploadDto.getFile(), "image");
        User user = userDetails.getUser();
        String content = uploadDto.getContent();
        Post post = new Post(user,content,imageUrl);
        postRepository.save(post);
        return imageUrl;
    }

    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }
}
