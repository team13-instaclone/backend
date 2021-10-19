package com.team13.teamplay2insta.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    public Post(Long id, String image, String content) {
        this.id = id;
        this.image = image;
        this.content = content;
    }

    public Post(PostRequestDto postRequestDto) {
        this.id = postRequestDto.getId();
        this.image = postRequestDto.getImage();
        this.content = postRequestDto.getContent();
    }

    public void update(PostRequestDto postRequestDto) {
        this.id = postRequestDto.getId();
        this.image = postRequestDto.getImage();
        this.content = postRequestDto.getContent();
    }
    public Post(User user, String content, String image){
        this.user = user;
        this.content = content;
        this.image = image;
    }


}
