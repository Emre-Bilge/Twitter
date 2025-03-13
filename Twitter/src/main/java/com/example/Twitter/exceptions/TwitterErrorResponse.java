package com.example.Twitter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class TwitterErrorResponse {

    private String message;

private int status ;

private long timestamp;

    public TwitterErrorResponse(String message, int status, long timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
