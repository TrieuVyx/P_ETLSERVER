package com.wilmar.p_server.exception;

public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException() {

        super("No data found");
    }
}
