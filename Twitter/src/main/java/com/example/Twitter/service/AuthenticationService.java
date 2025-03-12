package com.example.Twitter.service;

import com.example.Twitter.config.JwtService;
import com.example.Twitter.dto.AuthenticationRequest;
import com.example.Twitter.dto.AuthenticationResponse;
import com.example.Twitter.dto.RegisterRequest;
import com.example.Twitter.entity.Role;
import com.example.Twitter.entity.User;
import com.example.Twitter.repository.RoleRepository;
import com.example.Twitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



//register olma

    public AuthenticationResponse register(RegisterRequest registerRequest){

        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> roles = new HashSet<>();

User user = new User();
user.setUsername(registerRequest.getUsername());
user.setEmail(registerRequest.getEmail());
user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
user.setAuthorities(roles);

userRepository.save(user);

String accesToken = jwtService.generateAccessToken(user);
String refreshToken = jwtService.generateRefreshToken(user);

return AuthenticationResponse.builder()
        .accessToken(accesToken).refreshToken(refreshToken)
        .tokenType("Bearer").build();

    }


    //username ile doğrulama

public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){

        //user doğrulama
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(),authenticationRequest.getPassword()
));


User user=userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();

String accesToken = jwtService.generateAccessToken(user);
String refreshToken = jwtService.generateRefreshToken(user);


//builder kendisi oluşturuyor
return AuthenticationResponse.builder().accessToken(accesToken).refreshToken(refreshToken)
        .tokenType("Bearer").build();
}
}
