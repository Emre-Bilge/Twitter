package com.example.Twitter.service;

import com.example.Twitter.entity.Tweet;
import com.example.Twitter.entity.User;
import com.example.Twitter.exceptions.TweetNotFoundException;

import com.example.Twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TweetServiceImpl implements TweetService{

    private TweetRepository tweetRepository;


    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet create(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findTweetByTweetId(long tweetId) {

        return tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException("böyle bir tweet bulunamadı", HttpStatus.NOT_FOUND));
    }

    @Override
    public Tweet update(User user, Tweet tweet) {
        Tweet foundTweet = findTweetByTweetId(tweet.getId());

        boolean isTweetOwner = foundTweet.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));
       if(isTweetOwner || isAdmin){
           foundTweet.setContent(tweet.getContent());
         return tweetRepository.save(foundTweet);
       }
       throw new TweetNotFoundException("bu tweeti update edemezsiniz" , HttpStatus.FORBIDDEN);
    }

    @Override
    public void deleteTweet(long tweetId, User user) {

        Tweet tweet = findTweetByTweetId(tweetId);

        boolean isTweetOwner = tweet.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));

        if (!isTweetOwner && !isAdmin) {
throw new TweetNotFoundException("bu kullanıcı bu tweeti silemez",HttpStatus.FORBIDDEN);
        }
tweetRepository.delete(tweet);
    }

    @Override
    public List<Tweet> findTweetsByUserId(long userId) {

        List<Tweet> listedTweet = tweetRepository.findByUserId(userId);

        if(listedTweet.isEmpty()){
            throw new TweetNotFoundException("böyle bir tweet yok" , HttpStatus.NOT_FOUND);
        }
        return listedTweet;
    }
}
