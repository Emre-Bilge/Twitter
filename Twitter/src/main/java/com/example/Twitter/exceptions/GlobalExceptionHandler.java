package com.example.Twitter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
public ResponseEntity<TwitterErrorResponse>handleException(TwitterException twitterException){
    TwitterErrorResponse twitterErrorResponse = new TwitterErrorResponse(
twitterException.getMessage(),
            twitterException.getStatus().value(),
            System.currentTimeMillis()
    );

    return new ResponseEntity<>(twitterErrorResponse,twitterException.getStatus());

    }

    @ExceptionHandler
    public ResponseEntity<TwitterErrorResponse>handleException(Exception exception){
        TwitterErrorResponse twitterErrorResponse = new TwitterErrorResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500 hatası atması için bizden kaynaklı demek
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(twitterErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);

    }
//valdiasyon hatalarını yönetmek için yazılan exceptionhander.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest().body(errorMessage);
    }
}
