package com.example.Twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {

private String accessToken;

private String refreshToken;

@Builder.Default
private String tokenType = "Bearer";



}
