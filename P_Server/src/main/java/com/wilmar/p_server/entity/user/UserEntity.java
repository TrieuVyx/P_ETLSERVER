package com.wilmar.p_server.entity.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import com.wilmar.p_server.entity.role.RoleEntity;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String fullName;
    private String passWord;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER  , cascade = CascadeType.PERSIST)
    List<RoleEntity> roles;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleNameEntity().toString())));
        return authorities;
    }
}
