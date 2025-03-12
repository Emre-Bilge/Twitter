package com.example.Twitter.service;

import com.example.Twitter.entity.ReTweet;

public interface ReTweetService {

ReTweet reTweet(ReTweet reTweet);


void deleteReTweet(long userId , long tweetId);
}
