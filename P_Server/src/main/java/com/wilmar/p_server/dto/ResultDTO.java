package com.wilmar.p_server.dto;

import lombok.Data;

@Data
public class ResultDTO {
    private String userName;
    private String email;
    private String fullName;
    public ResultDTO(String userName , String email,String fullName) {
        this.userName = userName ;
        this.email = email;
        this.fullName =fullName;
    }

}
