package com.example.Twitter.repository;

import com.example.Twitter.entity.Like;
import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

Optional<Like> findByTweetUserIdTweetId(long userId , long tweetId);
}
