package com.wilmar.p_server.exception;


public class NotFoundId extends Throwable {
    public NotFoundId(Integer id) {
        super(String.format("khong tim thay " + String.valueOf(id)));
    }

}
