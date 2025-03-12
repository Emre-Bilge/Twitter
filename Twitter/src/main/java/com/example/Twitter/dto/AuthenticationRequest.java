package com.example.Twitter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "boş bırakılamaz")
    private String username ;

    @NotBlank(message = "boş bırakılamaz")
    private String password ;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
