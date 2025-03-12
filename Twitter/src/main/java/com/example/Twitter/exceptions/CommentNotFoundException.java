package com.example.Twitter.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommentNotFoundException extends TwitterException{

    public CommentNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
