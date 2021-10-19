package com.team13.teamplay2insta.repository;

import com.team13.teamplay2insta.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
