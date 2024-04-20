package com.wilmar.p_server.dto;


import lombok.*;

@Getter
@Setter
public class TokenDTO {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    public  TokenDTO (String accessToken, String tokenType, String refreshToken){
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken =refreshToken;
    }


}
