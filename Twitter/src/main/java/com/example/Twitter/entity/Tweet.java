package com.example.Twitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tweet",schema = "twitter")
public class Tweet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id ;

    @Column(name = "content")
    @NotNull(message = "boş bırakılamaz")
    private String content;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
    private User user ;


@OneToMany(mappedBy = "tweet" , cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private Set<Like> likes = new HashSet<>();


@OneToMany(mappedBy = "comment", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private Set<Comment> comments = new HashSet<>();

@OneToMany(mappedBy = "retweet" , cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private Set<ReTweet> retweets = new HashSet<>();
}
