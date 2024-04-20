package com.wilmar.p_server.service;


import com.wilmar.p_server.repository.RefreshTokenRepository;
import com.wilmar.p_server.exception.TokenRefreshException;
import com.wilmar.p_server.entity.token.RefreshToken;
import com.wilmar.p_server.repository.UserRepository;
import com.wilmar.p_server.security.JwtUtilities;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;


    public RefreshToken creaetRefreshToken(int id) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserEntity(userRepository.findById(id).get());
        refreshToken.setExpiration(Date.from(Instant.now().plus(60L, ChronoUnit.MINUTES)).toInstant());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;

    }

    public boolean verifyExpiration(String refreshToken) {

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(refreshToken);
        RefreshToken refreshTokenEntity = refreshTokenOptional.orElseThrow(() -> new TokenRefreshException("Refresh token not found"));

        if (refreshTokenEntity.getExpiration().compareTo(Date.from(Instant.now().plus(1L, ChronoUnit.MINUTES)).toInstant()) < 0) {
            throw new TokenRefreshException(refreshTokenEntity.getToken());
        }
        return true;
    }

}
