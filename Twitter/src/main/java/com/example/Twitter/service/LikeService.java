package com.example.Twitter.service;

import com.example.Twitter.entity.Like;

public interface LikeService {

Like likeTweet(Like like);

void unLikeTweet(long tweetId , long userId);
}
