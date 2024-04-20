package com.wilmar.p_server.security;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] permitApis = {"/api/auth/**"};
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(permitApis).permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    public CorsConfigurationSource corsConfigurationSource() {
        String[] cors = {"http://localhost:3000"};
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        for (String origin :
                cors) {
            configuration.addAllowedOrigin(origin);
        }
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(false);

        configuration.addAllowedHeader("**");
//        configuration.addAllowedHeader("Access-Control-Request-Method");
//        configuration.addAllowedHeader("Access-Control-Request-Headers");

        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("JWT-TOKEN");

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
