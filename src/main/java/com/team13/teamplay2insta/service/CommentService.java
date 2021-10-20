package com.team13.teamplay2insta.service;

import com.team13.teamplay2insta.dto.post.ResponseDto;
import com.team13.teamplay2insta.dto.comment.CommentRequestDto;
import com.team13.teamplay2insta.dto.comment.CommentUpdateResponseDto;
import com.team13.teamplay2insta.exception.*;
import com.team13.teamplay2insta.model.Comment;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    public final CommentRepository commentRepository;
    public final PostRepository postRepository;

    //댓글 추가
    public Comment addComment(Long postid, CommentRequestDto commentRequestDto, User user) {

        // 게시글 존재여부 확인
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new PostNotFoundException("해당 게시글을 찾을 수 없어 댓글을 추가할 수 없습니다."));

        Comment comment = new Comment(user, post, commentRequestDto.getComment());

        //댓글 추가
        return commentRepository.save(comment);
    }

//    댓글 수정
    @Transactional
    public ResponseDto modifyComment(Long commentId, String comment, User user) {

        // 댓글 존재여부 확인
        Comment commentObj = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글을 찾을 수 없어 수정할 수 없습니다."));

        //댓글에 저장되어있는 사용자의 username과 현재 사용자의 username 비교하기
        if(!commentObj.getUser().getUsername().equals(user.getUsername()))
            throw new AccessDeniedException("회원님의 댓글만 수정할 수 있습니다.");

        // 댓글 update
//        commentObj.set(commentRequestDto.getComment());
        commentObj.updateComment(comment);
        CommentUpdateResponseDto commentUpdateResponseDto = new CommentUpdateResponseDto(
                commentObj.getId(),
                commentObj.getComment(),
                commentObj.getUser().getUsername(),
                commentObj.getCreatedAt(),
                commentObj.getModifiedAt()
        );
        return new ResponseDto("success", commentUpdateResponseDto);
    }

    //댓글 삭제
    public void deleteComment(Long commentid, User user) {

     // 댓글 존재여부 확인
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new CustomErrorException("해당 댓글을 찾을 수 없어 삭제할 수 없습니다."));

        //댓글에 저장되어있는 사용자의 username과 현재 사용자의 username 비교하기
        if(!comment.getUser().getUsername().equals(user.getUsername()))
            throw new CustomErrorException("회원님의 댓글만 삭제 할 수 있습니다.");

        commentRepository.delete(comment);
    }




    public Page<Comment> getComments(User user) {
        return commentRepository.findAllByUserOrderByCreatedAtDesc(user, PageRequest.of(0,5));
    }
}