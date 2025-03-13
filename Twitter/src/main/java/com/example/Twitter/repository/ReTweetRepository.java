package com.example.Twitter.repository;

import com.example.Twitter.entity.ReTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReTweetRepository extends JpaRepository<ReTweet,Long> {
    @Query("SELECT r FROM ReTweet r WHERE r.user.id = :userId AND r.tweet.id = :tweetId")
    Optional<ReTweet> findTweetByUserIdAndTweetId(@Param("userId") long userId, @Param("tweetId") long tweetId);
}
