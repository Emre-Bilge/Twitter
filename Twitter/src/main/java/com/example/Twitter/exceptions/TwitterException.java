package com.example.Twitter.exceptions;

import org.springframework.http.HttpStatus;

public class TwitterException extends RuntimeException{

    private HttpStatus status ;


    public TwitterException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
