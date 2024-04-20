package com.wilmar.p_server.dto;

import lombok.*;

@Setter
@Getter
@Data
public class UserRes {
    private int id;
    private String userName;
    private String email;
    private String fullName;


}
