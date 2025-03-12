package com.example.Twitter.service;

import com.example.Twitter.entity.Comment;
import com.example.Twitter.exceptions.CommentNotFoundException;
import com.example.Twitter.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByTweetId(long tweetId) {
        return commentRepository.findByTweet_Id(tweetId);
    }



    @Override
    public void delete(String username, long commentId) {


       Comment comment = getByComment(commentId);


boolean isCommentOwner = comment.getUser().getUsername().equals(username);
boolean isTweetOwner = comment.getTweet().getUser().getUsername().equals(username);

if(!isCommentOwner && !isTweetOwner){
     throw new CommentNotFoundException("kendi yorumunuzu silebilirsiniz",HttpStatus.BAD_REQUEST);
}

commentRepository.delete(comment);

    }

    @Override
    public Comment getByComment(long commentId) {

        Optional<Comment> optionalComment =commentRepository.findById(commentId);

if(optionalComment.isPresent()){
    return optionalComment.get();
}
throw new CommentNotFoundException(commentId+ "id'li comment bulunmadı",HttpStatus.NOT_FOUND);

    }


}
