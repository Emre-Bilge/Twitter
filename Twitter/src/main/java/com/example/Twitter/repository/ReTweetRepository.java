package com.example.Twitter.repository;

import com.example.Twitter.entity.ReTweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReTweetRepository extends JpaRepository<ReTweet,Long> {

Optional<ReTweet> findTweetByUserIdByTweetId(long userId,long tweetId);
}
