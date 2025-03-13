package com.example.Twitter.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

private String secretKey ="MySecretKey1234567890!@#2025";

    private long accessTokenExpiration = 5*60*60*1000; // 5 saat
    private long refreshTokenExpiration =24*60*60*1000; //1 gün

    public String getSecretKey() {
        return secretKey;
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}
