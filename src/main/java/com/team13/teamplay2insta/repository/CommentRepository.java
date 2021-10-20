package com.team13.teamplay2insta.repository;

import com.team13.teamplay2insta.model.Comment;
import com.team13.teamplay2insta.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    List<Comment> findAllByPostId(Long post_id);
}