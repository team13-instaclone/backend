package com.team13.teamplay2insta.repository;

import com.team13.teamplay2insta.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Post 내림차순 정렬
    List<Post> findAllByOrderByCreatedAtDesc();

}
