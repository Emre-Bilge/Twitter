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
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@Data
public class AuthenticationService {

    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private  PasswordEncoder passwordEncoder;
    private  JwtService jwtService;
    private  AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

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

String accessToken = jwtService.generateAccessToken(user);
String refreshToken = jwtService.generateRefreshToken(user);

    /*    AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accesToken)
                .refreshToken(refreshToken)
                .build();


        return response;*/

        return new AuthenticationResponse(accessToken, refreshToken);
    }


    //username ile doğrulama

public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){

        //user doğrulama
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(),authenticationRequest.getPassword()
));


User user=userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();

String accessToken = jwtService.generateAccessToken(user);
String refreshToken = jwtService.generateRefreshToken(user);


//builder kendisi oluşturuyor
/*return AuthenticationResponse.builder().accessToken(accesToken).refreshToken(refreshToken)
        .tokenType("Bearer").build();*/
    return new AuthenticationResponse(accessToken, refreshToken, "Bearer");
}
}
