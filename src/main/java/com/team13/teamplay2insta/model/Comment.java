package com.team13.teamplay2insta.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String comment; //댓글내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

}
