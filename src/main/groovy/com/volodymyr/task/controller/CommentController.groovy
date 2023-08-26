package com.volodymyr.task.controller

import com.volodymyr.task.service.CommentService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comment")
class CommentController {

    @Autowired
    CommentService commentService

    @DeleteMapping
    ResponseEntity<String> deleteComment(Authentication authentication, ObjectId commentId){
        commentService.deleteComment(commentId,authentication)
        return ResponseEntity.ok("Success")
    }

}
