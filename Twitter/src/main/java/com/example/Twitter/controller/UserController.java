package com.example.Twitter.controller;

import com.example.Twitter.service.UserService;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "http://localhost:3202") artık Cors içinde yönetiyoruz
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


@GetMapping("/{username}")
    @Transactional
    public ResponseEntity<?> findByUsername(@PathVariable String username){

        try {
        UserDetails userDetails = userService.loadUserByUsername(username);
    return ResponseEntity.ok(userDetails);


        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("böyle bir kullanıcı yok" + username);
        }
}
}
