package com.wilmar.p_server.exception;

import lombok.AllArgsConstructor;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

    public TokenRefreshException(String token) {
        super(String.format("Failed for ", token));
    }

}