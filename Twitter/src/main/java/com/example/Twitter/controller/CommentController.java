package com.example.Twitter.controller;

import com.example.Twitter.entity.Comment;
import com.example.Twitter.entity.User;
import com.example.Twitter.exceptions.CommentNotFoundException;

import com.example.Twitter.exceptions.GlobalExceptionHandler;
import com.example.Twitter.exceptions.TwitterException;
import com.example.Twitter.service.CommentService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
//@CrossOrigin(origins = "http://localhost:3202") artık Cors içinde yönetiyoruz
public class CommentController {

   private CommentService commentService;

   @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Transactional
    public Comment createComment(@RequestBody Comment comment , @AuthenticationPrincipal User user){
comment.setUser(user);

if(!comment.getUser().getId().equals(user.getId())){
throw new CommentNotFoundException("bu user yetkili değil" , HttpStatus.FORBIDDEN);
}
    return commentService.createComment(comment);
}

@GetMapping("/tweet/{id}")
    @Transactional
    public List<Comment>  getCommentsByTweetId(@PathVariable long id){
    return commentService.getCommentsByTweetId(id);
}

@DeleteMapping("/tweet/{id}/user/{userId}")
    @Transactional
public void deleteComment(@PathVariable long id ,@PathVariable Long userId ,@AuthenticationPrincipal User user){

       if(!userId.equals(user.getId())){
throw new TwitterException("yetkiniz yok ", HttpStatus.FORBIDDEN);

}
       commentService.delete(user.getUsername(), id);

       }


}

