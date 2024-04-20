package com.wilmar.p_server.dto;
import org.springframework.http.HttpStatus;
import java.util.Date;
import lombok.*;


@Getter
@Setter
public class HttpMessage {

    private HttpStatus httpStatus;
    private Date timeStamp;
    private String message;
    private String description;
    public HttpMessage(HttpStatus httpStatus,Date timeStamp,String message,String description){
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.message = message;
        this.description =description;
    }

}
