package com.team13.teamplay2insta.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    private String username; //닉네임

    private String pwd;

    public LoginRequestDto(String username, String pwd){
        this.username = username;
        this.pwd = pwd;
    }
}
