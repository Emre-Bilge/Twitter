package com.example.Twitter.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyRegisteredException extends TwitterException{

    public UserAlreadyRegisteredException(String message, HttpStatus status) {
        super(message, status);
    }
}
