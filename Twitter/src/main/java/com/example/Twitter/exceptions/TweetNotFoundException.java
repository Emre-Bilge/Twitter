package com.example.Twitter.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TweetNotFoundException extends TwitterException{

    public TweetNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
