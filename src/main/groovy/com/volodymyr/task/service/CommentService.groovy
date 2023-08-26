package com.volodymyr.task.service

import com.volodymyr.task.entity.Comment
import com.volodymyr.task.entity.User
import com.volodymyr.task.repository.CommentRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CommentService {

    @Autowired
    CommentRepository commentRepository
    @Autowired
    UserService userService

    boolean deleteComment(ObjectId commentId, Authentication authentication){
        User user = userService.getAuthenticatedUser(authentication)
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("No such element"))
        if(user == comment.getOwner()){
            commentRepository.delete(comment)
        }else {
            throw new RuntimeException("Forbidden")
        }
        return true
    }

}
