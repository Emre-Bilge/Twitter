package com.example.Twitter.controller;

import com.example.Twitter.entity.ReTweet;
import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;
import com.example.Twitter.exceptions.ReTweetNotFoundException;
import com.example.Twitter.exceptions.TweetNotFoundException;
import com.example.Twitter.service.ReTweetService;
import com.example.Twitter.service.TweetService;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweets")
//@CrossOrigin(origins = "http://localhost:3202") artık Cors içinde yönetiyoruz
public class ReTweetController {

    private TweetService tweetService;
private ReTweetService reTweetService;


@Autowired
public ReTweetController(TweetService tweetService, ReTweetService reTweetService) {
        this.tweetService = tweetService;
        this.reTweetService = reTweetService;
    }

@PostMapping
    @Transactional
    public ReTweet reTweet(@RequestBody Tweet tweet , @AuthenticationPrincipal User user){
Tweet tweeted =tweetService.findTweetByTweetId(tweet.getId());
if(tweeted == null){
throw new TweetNotFoundException("böyle bir tweet yok " , HttpStatus.NOT_FOUND);
}

ReTweet reTweet = new ReTweet();
reTweet.setTweet(tweeted);
reTweet.setUser(user);

    return reTweetService.reTweet(reTweet);
}

@PostMapping("/tweet/{tweetId}/user/{userId}")
    @Transactional
    public void deleteReTweet(@PathVariable Long tweetId , @PathVariable Long userId , @AuthenticationPrincipal User user){
if(!userId.equals(user.getId())){
    throw new  ReTweetNotFoundException("buna yetkiniz yok" , HttpStatus.FORBIDDEN);
}
reTweetService.deleteReTweet(tweetId,userId);
}

}
