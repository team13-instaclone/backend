package com.team13.teamplay2insta.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "댓글 정보", description = "댓글아이디, 유저아이디, 게시글아이디, 댓글내용을 가진 Model Class")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    @ApiModelProperty(value = "댓글아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @ApiModelProperty(value = "유저아이디")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    @ApiModelProperty(value = "게시글아이디")
    private Post post;

    @Column
    @ApiModelProperty(value = "댓글내용")
    private String comment; //댓글내용

    public Comment(User user, Post post, String comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public void updateComment(String comment){
        this.comment = comment;
    }
}
