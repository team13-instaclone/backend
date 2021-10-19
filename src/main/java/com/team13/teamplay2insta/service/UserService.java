package com.team13.teamplay2insta.service;

import com.team13.teamplay2insta.dto.ResponseDto;
import com.team13.teamplay2insta.dto.SignupRequestDto;
import com.team13.teamplay2insta.exception.CustomErrorException;
import com.team13.teamplay2insta.model.User;
import com.team13.teamplay2insta.repository.UserRepository;
import com.team13.teamplay2insta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public  ResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String name = signupRequestDto.getName();//실명
        System.out.println("UserService:"+username);

//        회원 ID 중복 확인
        checkRedunbancy(username);
        //패스워드 암호화
        String encodedPwd= passwordEncoder.encode(signupRequestDto.getPwd());

        User user = new User(username,name,encodedPwd);
        System.out.println("UserService의 User:"+user.getUsername());
        User savedUser = userRepository.save(user);
        System.out.println(savedUser.getUsername());
        return new ResponseDto("success","회원가입 성공");
    }

    public void checkRedunbancy(String username) {
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomErrorException("중복된 유저네임이 존재합니다.");
        }
    }

    //로그인
    public User login(String username, String pwd) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomErrorException("유저네임을 찾을 수 없습니다.")
        );

        // 패스워드 암호화
        if (!passwordEncoder.matches(pwd, user.getPwd())) {
            throw new CustomErrorException("비밀번호가 맞지 않습니다.");
        }
        return user;
    }

    public User loadLoginUser(UserDetailsImpl userDetails){
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                ()->new CustomErrorException("로그인된 유저의 정보를 찾을 수 없습니다")
        );
    }
}


















