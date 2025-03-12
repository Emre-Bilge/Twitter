package com.example.Twitter.config;

import com.example.Twitter.exceptions.GlobalExceptionHandler;
import com.example.Twitter.exceptions.TwitterException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtService {

    private JwtProperties jwtProperties;

    // claim'i token'dan çıkarma işlemi
    public String extractUsername(String token) {
        String username = extractClaim(token, Claims::getSubject);
        return username;
    }

    // JWT'den belirli claim'i almak için genel metot
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //tüm claimleri yani verileri tokenden çıkarır
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new TwitterException("boyle token yok",HttpStatus.NOT_FOUND);
        }
    }
    //jwt imzalama ve doğrulama için secret keyi alır
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //kullanıcı için access token üretir
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    //user kimliğini doğrulamak için kısa süreli access token üret
    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtProperties.getAccessTokenExpiration());
    }

    //access token süresi dolunca refresh token üretir
    public String generateRefreshToken(UserDetails userDetails) {

        return buildToken(new HashMap<>(), userDetails, jwtProperties.getRefreshTokenExpiration());
    }

    //jwt oluşturur -- subject olarak user eklenir -- expiration eklenir -- imzalanır
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // token geçerlimi
    public boolean isTokenValid(String token , UserDetails userDetails){
final String username = extractUsername(token);
boolean isValid =(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
return isValid;
    }

    // token süresi dolmus mu
private boolean isTokenExpired(String token){
return extractExpiration(token).before(new Date());
}

private Date extractExpiration(String token){
return extractClaim(token , Claims::getExpiration);
}

}
