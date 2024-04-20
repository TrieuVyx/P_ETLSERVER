package com.wilmar.p_server.dto;
import lombok.Data;

@Data

public class UserDTO  {
    private int id;
    private String userName;
    private String email;
    private String fullName;
    private String passWord;

}
