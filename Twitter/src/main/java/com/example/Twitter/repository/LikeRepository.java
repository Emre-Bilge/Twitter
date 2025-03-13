package com.example.Twitter.repository;

import com.example.Twitter.entity.Like;
import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    @Query("SELECT l FROM Like l WHERE l.tweet.user.id = :userId AND l.tweet.id = :tweetId")
Optional<Like> findByTweetUserIdTweetId(long userId , long tweetId);
}
