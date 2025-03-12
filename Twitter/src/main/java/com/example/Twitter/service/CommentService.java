package com.example.Twitter.service;

import com.example.Twitter.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment);

    List<Comment> getCommentsByTweetId(long tweetId);

    void delete(String username,long commentId);


    Comment getByComment(long commentId);
}
