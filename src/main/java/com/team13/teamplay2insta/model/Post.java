package com.team13.teamplay2insta.model;

import com.team13.teamplay2insta.dto.post.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    
    public Post(User user, String content, String image){
        this.user = user;
        this.content = content;
        this.image = image;
    }

    public void updatePost(String content, String image){
        this.content = content;
        this.image = image;
    }


}
