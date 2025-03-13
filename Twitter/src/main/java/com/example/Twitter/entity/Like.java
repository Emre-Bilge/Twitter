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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
