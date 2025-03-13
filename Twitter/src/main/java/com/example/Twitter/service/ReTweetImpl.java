package com.example.Twitter.service;

import com.example.Twitter.entity.ReTweet;
import com.example.Twitter.exceptions.ReTweetNotFoundException;
import com.example.Twitter.repository.ReTweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReTweetImpl implements ReTweetService{

    private ReTweetRepository reTweetRepository;

@Autowired
    public ReTweetImpl(ReTweetRepository reTweetRepository) {
        this.reTweetRepository = reTweetRepository;
    }



    @Override
    public ReTweet reTweet(ReTweet reTweet) {

        Optional<ReTweet> optionalReTweet = reTweetRepository.findTweetByUserIdAndTweetId(reTweet.getUser().getId(),reTweet.getTweet().getId());

        if(optionalReTweet.isEmpty()){
            throw new ReTweetNotFoundException("böyle bir tweet yok" , HttpStatus.NOT_FOUND);
        }
       return reTweetRepository.save(reTweet);



    }

    @Override
    public void deleteReTweet(long userId , long tweetId) {

    Optional<ReTweet> optionalReTweet = reTweetRepository.findTweetByUserIdAndTweetId(userId , tweetId);
    if(optionalReTweet.isEmpty()){
        throw new ReTweetNotFoundException("böyle bir retweet yok " , HttpStatus.NOT_FOUND);
    }
    reTweetRepository.delete(optionalReTweet.get());
    }
}
