package com.wilmar.p_server.dto;

import com.wilmar.p_server.entity.user.UserEntity;
import java.util.*;
import lombok.Data;


@Data
public class ResponUserDTO {
    private int totalPages;
    private Long totalElements;
    private List<UserRes> users = new ArrayList<UserRes>();
    public ResponUserDTO(int totalPages, long totalElements, List<UserEntity> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        for (UserEntity userEntity: content) {
            UserRes userRes = new UserRes();
            userRes.setId(userEntity.getId());
            userRes.setEmail(userEntity.getEmail());
            userRes.setUserName(userEntity.getUserName());
            userRes.setFullName(userEntity.getFullName());
            users.add(userRes);
        }
    }
}
