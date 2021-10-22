package com.team13.teamplay2insta.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.team13.teamplay2insta.awsS3.S3Uploader;
import com.team13.teamplay2insta.dto.post.MainResponseDto;
import com.team13.teamplay2insta.dto.post.PostUpdateRequestDto;
import com.team13.teamplay2insta.dto.post.PostUpdateResponseDto;
import com.team13.teamplay2insta.dto.post.PostUploadDto;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;
    private final AmazonS3Client amazonS3Client;
    private final String bucket = "insta-image-upload";

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

        public List<MainResponseDto> getAllContents() {
            List<Post> postlist = postRepository.findAllByOrderByCreatedAtDesc();
            List<MainResponseDto> mainResponseDtos = new ArrayList<>();

            for(Post post : postlist){
                MainResponseDto mainResponseDto = new MainResponseDto(
                        post.getId(),
                        post.getUser().getUsername(),
                        post.getImage(),
                        post.getCreatedAt(),
                        post.getModifiedAt(),
                        post.getContent()
                );

                mainResponseDtos.add(mainResponseDto);
            }

            return mainResponseDtos;
        }




    public String uploadPost(PostUploadDto uploadDto, UserDetailsImpl userDetails) throws IOException {
        String imageUrl = s3Uploader.upload(uploadDto.getFile(), "image");
        User user = userDetails.getUser();
        String content = uploadDto.getContent();
        Post post = new Post(user,content,imageUrl);
        postRepository.save(post);
        return imageUrl;
    }




    @Transactional
    public PostUpdateResponseDto updatePost(PostUpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        Long postId = updateRequestDto.getPostid();
        // 게시글 존재여부 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("해당 게시글을 찾을 수 없어 수정할 수 없습니다."));

        String imageUrl = post.getImage(); //사진은 일단 기존 포스트의 URL(사진은 업데이트 안 했을 경우 대비)
        User user = userDetails.getUser();
        String content = updateRequestDto.getContent();

        //게시글에 저장되어있는 사용자의 username과 현재 사용자의 username 비교하기
        if(!post.getUser().getUsername().equals(user.getUsername()))
            throw new CustomErrorException("회원님의 게시글만 수정할 수 있습니다.");

        //기존에 S3에 있는 사진 삭제하기기
        if(updateRequestDto.getImage() instanceof MultipartFile){
            //사진도 함께 업데이트 하는 경우: 기존사진 삭제후 업로드
            Post foundPost = postRepository.findById(postId).orElseThrow(()->
                    new CustomErrorException("해당작품을 찾을 수 없습니다"));
            deleteS3(foundPost.getImage());
            imageUrl = s3Uploader.upload(updateRequestDto.getImage(), "image");
            if(imageUrl == null) throw new CustomErrorException("이미지 업르드에 실패하였습니다");
        }

        // 게시글 update
        post.updatePost(content, imageUrl);

        PostUpdateResponseDto postUpdateResponseDto = new PostUpdateResponseDto(
                post.getId(),
                post.getImage(),
                post.getUser().getUsername(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
        return postUpdateResponseDto;
    }

    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->
                new CustomErrorException("해당 아이디의 포스트가 존재하지 않습니다"));
        postRepository.deleteById(postId);
        //s3서버의 이미지도 지우기
        deleteS3(post.getImage());
    }



    public void deleteS3(@RequestParam String imageName){
        //https://S3 버킷 URL/버킷에 생성한 폴더명/이미지이름
        String keyName = imageName.split("/")[4]; // 이미지이름만 추출

        try {amazonS3Client.deleteObject(bucket + "/image", keyName);
        }catch (AmazonServiceException e){
            e.printStackTrace();
            throw new AmazonServiceException(e.getMessage());
        }
    }
}
