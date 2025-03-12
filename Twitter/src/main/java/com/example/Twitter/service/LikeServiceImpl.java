package com.example.Twitter.service;

import com.example.Twitter.entity.Like;
import com.example.Twitter.exceptions.GlobalExceptionHandler;
import com.example.Twitter.exceptions.LikeNotFoundException;
import com.example.Twitter.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{


    private LikeRepository likeRepository;

@Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public Like likeTweet(Like like) {

    Optional<Like> optionalLike = likeRepository.findByTweetUserIdTweetId(like.getUser().getId(),like.getTweet().getId());

    if(optionalLike.isPresent()){
return optionalLike.get();
    }
     return likeRepository.save(like);
    }

    @Override
    public void unLikeTweet(long tweetId, long userId) {
Optional<Like> likedTweet = likeRepository.findByTweetUserIdTweetId(userId,tweetId);

if(likedTweet.isEmpty()){
    throw new LikeNotFoundException("böyle bir like yok" , HttpStatus.NOT_FOUND);
}
 likeRepository.delete(likedTweet.get());
    }
}
