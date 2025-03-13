package com.example.Twitter.controller;

import com.example.Twitter.dto.AuthenticationRequest;
import com.example.Twitter.dto.AuthenticationResponse;
import com.example.Twitter.dto.RegisterRequest;
import com.example.Twitter.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3202") artık Cors içinde yönetiyoruz
public class AuthenticationController {

    private AuthenticationService authenticationService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
return ResponseEntity.ok(authenticationService.register(registerRequest));

}

@PostMapping("/login")
public ResponseEntity<AuthenticationResponse>authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
}

}
