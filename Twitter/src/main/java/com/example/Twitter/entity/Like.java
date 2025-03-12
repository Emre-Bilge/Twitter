package com.example.Twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "like",schema = "twitter")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id ;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
    private User user ;

@JoinColumn(name = "tweet_id" )
    @ManyToOne(fetch = FetchType.LAZY)
    private Tweet tweet;
}
