package com.team13.teamplay2insta.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDataDto {
    String token;
    String username;
    String name;
}
