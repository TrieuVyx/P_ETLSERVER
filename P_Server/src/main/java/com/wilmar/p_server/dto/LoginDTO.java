package com.wilmar.p_server.dto;


import lombok.*;


@Setter
@Getter
@Data
public class LoginDTO {

    private String email;
    private String passWord;
}
