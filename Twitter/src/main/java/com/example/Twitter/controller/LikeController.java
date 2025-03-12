package com.example.Twitter.controller;

import com.example.Twitter.entity.Like;
import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;
import com.example.Twitter.exceptions.LikeNotFoundException;
import com.example.Twitter.exceptions.TweetNotFoundException;
import com.example.Twitter.exceptions.TwitterException;
import com.example.Twitter.service.LikeService;
import com.example.Twitter.service.TweetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@CrossOrigin(origins = "http://localhost:3200")
public class LikeController {

    private LikeService likeService;
    private TweetService tweetService;

@Autowired
    public LikeController(LikeService likeService, TweetService tweetService) {
        this.likeService = likeService;
        this.tweetService = tweetService;
    }

@PostMapping
    @Transactional
public ResponseEntity<Like>likeTweet(@RequestBody Like like , @AuthenticationPrincipal User user){
    Tweet tweet =tweetService.findTweetByTweetId(like.getTweet().getId());
if(tweet == null){
    throw new TweetNotFoundException("böyle bir tweet yok ", HttpStatus.NOT_FOUND);


}
    like.setTweet(tweet);
    like.setUser(user);
    Like createdLike =likeService.likeTweet(like);
    return ResponseEntity.ok(createdLike);
}

@DeleteMapping("/tweet/{tweetId}/user/{userId}")
    @Transactional
public void deleteComment(@PathVariable Long tweetId ,@PathVariable Long userId ,@AuthenticationPrincipal User user){
    if(!userId.equals(user.getId())){
        throw new LikeNotFoundException("yetkiniz yok ", HttpStatus.FORBIDDEN);

    }
    likeService.unLikeTweet(tweetId,userId);

}



}
