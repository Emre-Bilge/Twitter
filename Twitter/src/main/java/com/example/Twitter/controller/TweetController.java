package com.example.Twitter.controller;

import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;
import com.example.Twitter.exceptions.TweetNotFoundException;
import com.example.Twitter.exceptions.TwitterException;
import com.example.Twitter.service.TweetService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/tweets")
//@CrossOrigin(origins = "http://localhost:3202") artık Cors içinde yönetiyoruz
public class TweetController {

private TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

@PostMapping
    @Transactional
    public Tweet tweet(@RequestBody Tweet tweet , @AuthenticationPrincipal User user){
if(user == null){
    throw new TwitterException("böyle bir user yok" , HttpStatus.FORBIDDEN);
}
tweet.setUser(user);

Tweet createdtweet =tweetService.create(tweet);

return createdtweet;
}

@GetMapping
 public List<Tweet> getAllTweets(){
List<Tweet> tweets = tweetService.getAllTweets();
if(tweets.isEmpty()){
throw new  TweetNotFoundException("böyle bir tweet yok ", HttpStatus.NOT_FOUND);
}
return tweets ;
}

@GetMapping("/{tweetId}")
    public ResponseEntity<Tweet> findTweetById(@PathVariable Long tweetId){
    Tweet tweet = tweetService.findTweetByTweetId(tweetId) ;
    if(tweet == null){
        throw new TweetNotFoundException("böyle bir tweet yok" , HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(tweet);
}

@DeleteMapping("/{tweetId}")
    @Transactional
    public ResponseEntity<Void> deleteTweet(@PathVariable Long tweetId , @AuthenticationPrincipal User user){
if(user == null){
return ResponseEntity.status(401).build();
}
tweetService.deleteTweet(tweetId,user);
return ResponseEntity.ok().build();
}

}
