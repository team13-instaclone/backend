package com.team13.teamplay2insta.controller;

import com.team13.teamplay2insta.dto.LoginRequestDto;
import com.team13.teamplay2insta.dto.ResponseDto;
import com.team13.teamplay2insta.dto.SignupRequestDto;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.security.jwt.JwtTokenProvider;
import com.team13.teamplay2insta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    //회원가입
    @PostMapping("/api/user/signup")
    public ResponseDto signUp(@RequestBody SignupRequestDto signupRequestDto) {
        System.out.println("UserController:"+signupRequestDto.getName());
        return userService.signup(signupRequestDto);
    }

    // 로그인
    @PostMapping("/api/user/login")
    public ResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userService.login(requestDto.getUsername(), requestDto.getPwd());
        String checkedUsername = user.getUsername();
        String token = jwtTokenProvider.createToken(checkedUsername);

        response.setHeader("X-AUTH-TOKEN", token);
        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        //body에도 보내주기 혹시모르니까
        return new ResponseDto("success",token);
    }

    //유저이름 중복확인
    @PostMapping("/api/user/redunbancy")
    public ResponseDto checkRedunbancy(@RequestBody String username){

        userService.checkRedunbancy(username);

        return new ResponseDto("success","중복되지 않습니다");
    }
}















