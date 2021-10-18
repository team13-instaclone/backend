package com.team13.teamplay2insta.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; //닉네임

    @Column(nullable = false)
    private String name;//실명

    @Column(nullable = false)
    private String password;

    //user가 쓴 글 목록 볼 경우를 대비해 만듦
    @OneToMany(mappedBy = "user")
    private List<Post> post;
}
