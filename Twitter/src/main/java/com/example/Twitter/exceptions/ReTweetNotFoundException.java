package com.example.Twitter.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReTweetNotFoundException extends TwitterException{

    public ReTweetNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
