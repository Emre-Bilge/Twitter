package com.example.Twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "authentication")
public class Authentication {


    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
    private Long id ;

    @Column(name = "token")
    @NotNull(message = "boş olamaz")
    private String token ;

    @Column(name = "expires_at")
    @NotNull(message = "boş olamaz")
    private String expiresAt;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user ;
}
