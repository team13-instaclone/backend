package com.team13.teamplay2insta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
public class PostRequestDto {

    private Long id;

    @NotBlank
    private String image;

    @NotBlank
    private String content;

}
