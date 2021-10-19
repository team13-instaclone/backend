package com.team13.teamplay2insta.service;

import com.team13.teamplay2insta.dto.ResponseDto;
import com.team13.teamplay2insta.dto.comment.CommentRequestDto;
import com.team13.teamplay2insta.exception.*;
import com.team13.teamplay2insta.exception.defaultResponse.DefaultResponse;
import com.team13.teamplay2insta.exception.defaultResponse.StatusCode;
import com.team13.teamplay2insta.exception.defaultResponse.SuccessYn;
import com.team13.teamplay2insta.model.Comment;
import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.repository.PostRepository;
import com.team13.teamplay2insta.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

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

    //댓글 수정
//    public ResponseEntity modifyComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
//
//        Long articleId = commentRequestDto.getPostid();
//
//        // 화면에서 들어온 commentID를 이용하여 DB에 있는 articleID로 게시글 존재여부 확인
//        Post post = postRepository.findById(articleId).orElseThrow(
//                () -> new PostNotFoundException("해당 게시글을 찾을 수 없어 댓글을 수정할 수 없습니다."));
//
//        // 댓글 존재여부 확인
//        Comment comment = commentRepository.findById(commentId).orElseThrow(
//                () -> new CommentNotFoundException("해당 댓글을 찾을 수 없어 수정할 수 없습니다."));
//
//        // DB에 있는 comment 의 articleId와 DB에 있는 post ID를 비교
//        if(!post.getId().equals(comment.getPost().getId()))
//            new PostNotFoundException("해당 게시글을 또는 댓글의 정보가 잘못되었습니다. 관리자 확인이 필요합니다.");
//
//        //댓글에 저장되어있는 사용자의 username과 현재 사용자의 username 비교하기
//        if(!comment.getUser().getUsername().equals(user.getUsername()))
//            throw new AccessDeniedException("회원님의 댓글만 수정할 수 있습니다.");
//
//        // 댓글 update
//        comment.setComment(commentRequestDto.getComment());
//        commentRepository.save(comment);
//        return new ResponseEntity(DefaultResponse.res(SuccessYn.OK, StatusCode.OK, "댓글 수정이 완료되었습니다.", null), HttpStatus.OK);
//    }

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