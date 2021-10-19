package com.team13.teamplay2insta.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {

    private String username; //닉네임

    private String name;//실명

    private String pwd;

    public SignupRequestDto(String username, String name, String pwd){
        this.username = username;
        this.name = name;
        this.pwd = pwd;
    }
}
