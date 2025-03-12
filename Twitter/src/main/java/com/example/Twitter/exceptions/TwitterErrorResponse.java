package com.example.Twitter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwitterErrorResponse {

    private String message;

private int status ;

private long timestamp;
}
