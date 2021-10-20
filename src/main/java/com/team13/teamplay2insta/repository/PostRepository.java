package com.team13.teamplay2insta.repository;

import com.team13.teamplay2insta.model.Post;
import com.team13.teamplay2insta.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    List<Post> findAllByOrderByCreatedAtDesc();
}