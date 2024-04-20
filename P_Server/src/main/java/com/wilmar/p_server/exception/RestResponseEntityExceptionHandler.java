package com.wilmar.p_server.exception;

import com.wilmar.p_server.dto.HttpMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { CustomNotFoundException.class})
    protected ResponseEntity<Object> handleCustomNotFoundException(
            CustomNotFoundException ex, WebRequest request) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("httpStatus",  HttpStatus.NOT_FOUND);
            body.put("message",  "not found");
            body.put("timeStamp",  new Date());
            body.put("description",  request.getDescription(false));
            return handleExceptionInternal(ex, body,
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(NotFoundId.class)
    protected ResponseEntity<Object> handleNotFoundId(
            NotFoundId ex,WebRequest request) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("httpStatus", HttpStatus.NOT_FOUND);
            body.put("timestamp", LocalDateTime.now());
            body.put("message", ex.getMessage());
            body.put("description", request.getDescription(false));
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(ForbiddenException.class)
//    protected ResponseEntity<Object> handleForbiddenException(){
//
//    }

}