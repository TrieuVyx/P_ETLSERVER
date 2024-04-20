package com.wilmar.p_server.entity.role;


import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Enumerated(EnumType.STRING)
    RoleNameEntity roleNameEntity;

}
