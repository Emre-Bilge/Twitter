package com.example.Twitter.repository;

import com.example.Twitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTweet_Id(Long tweetId);
}







