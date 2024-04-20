package com.wilmar.p_server.mapper;

import com.wilmar.p_server.entity.user.UserEntity;
import com.wilmar.p_server.dto.UserDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    UserDTO dtoMapToUser (UserEntity userEntity);
    UserEntity userMapToDTO(UserDTO userDTO);

}
