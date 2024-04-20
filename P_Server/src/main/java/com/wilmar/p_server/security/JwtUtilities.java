package com.wilmar.p_server.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.*;
import java.time.*;
import java.util.*;

@Slf4j
@Component
public class JwtUtilities {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;


    public String generateToken(String email, List<String> roles){
        return Jwts.builder()
                .setSubject(email)
                .claim("USER",roles)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1L, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7); }
        return null;
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
