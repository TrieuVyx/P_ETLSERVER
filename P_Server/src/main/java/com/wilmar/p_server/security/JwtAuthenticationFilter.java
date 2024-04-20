package com.wilmar.p_server.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.wilmar.p_server.entity.user.UserEntity;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;
import com.wilmar.p_server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.FilterChain;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtilities jwtUtilities;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtilities.getToken(request);

        if(token != null && jwtUtilities.validateToken(token)){
            String email = jwtUtilities.getEmailFromToken(token);
            UserEntity userEntity = userService.findByEmail(email);
            if(userEntity!= null){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userEntity.getEmail(),null,userEntity.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
