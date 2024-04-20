package com.wilmar.p_server.entity.token;

import com.wilmar.p_server.entity.user.UserEntity;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;


@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiration;
}
