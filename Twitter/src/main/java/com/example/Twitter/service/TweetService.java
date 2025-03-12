package com.example.Twitter.service;

import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;

import java.util.List;

public interface TweetService {

    Tweet create(Tweet tweet);

    List<Tweet> getAllTweets();

    Tweet findTweetByTweetId(long tweetId);

    Tweet update(User user, Tweet tweet);

    void deleteTweet(long tweetId , User user);

    List<Tweet> findTweetsByUserId(long userId);
}
