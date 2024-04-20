package com.wilmar.p_server.dto;

import lombok.Data;

@Data
public class ResultId {
    private Integer id;
    private String userName;
    private String email;
    private String fullName;
    public ResultId(String userName , String email, String fullName, Integer id) {
        this.id = id;
        this.userName = userName ;
        this.email = email;
        this.fullName =fullName;
    }


}
