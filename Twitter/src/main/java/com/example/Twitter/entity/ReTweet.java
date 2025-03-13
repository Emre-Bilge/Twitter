package com.example.Twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="retweet",schema = "twitter")
public class ReTweet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id ;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
private Tweet tweet ;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
