package com.volodymyr.task.controller

import com.volodymyr.task.entity.Post
import com.volodymyr.task.service.PostService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/post")
class PostController {

    @Autowired
    PostService postService

    @GetMapping
    ResponseEntity<List<Post>> getPost(Authentication authentication, String userName) {
        return ResponseEntity.ok(postService.getPosts(authentication, userName))
    }

    @PostMapping("/create")
    ResponseEntity<String> createPost(Authentication authentication, @RequestParam(required = true) String text) {
        postService.createPost(authentication, text)
        return ResponseEntity.ok("Created")
    }

    @PutMapping("/{postId}/like")
    ResponseEntity<String> changeLikeStatus(Authentication authentication, @PathVariable ObjectId postId) {
        postService.likeOrDislikePost(authentication, postId)
        return ResponseEntity.ok("Success")
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<String> deletePost(Authentication authentication, @PathVariable ObjectId postId) {
        postService.deletePost(authentication, postId)
        return ResponseEntity.ok("Success")
    }

    @PutMapping("/{postId}/add-comment")
    ResponseEntity<String> addCommentToPost(Authentication authentication,
                                            @PathVariable ObjectId postId,
                                            @RequestParam(required = true) String comment) {
        postService.addComment(authentication, postId, comment)
        return ResponseEntity.ok("Success")
    }

    @PutMapping("/{postId}/change-post")
    ResponseEntity<String> changePost(Authentication authentication,
                                      @PathVariable ObjectId postId,
                                      @RequestParam(required = true) String newComment) {
        postService.changePost(authentication, postId, newComment)
        return ResponseEntity.ok("Success")
    }

    @GetMapping("/{postId}/comments")
    ResponseEntity<String> getComments(@PathVariable ObjectId postId) {
        postService.getComments(postId)
        return ResponseEntity.ok("Success")
    }

    @GetMapping("/get-news-feed")
    ResponseEntity<List<Post>> getNewsFeed(Authentication authentication) {
        return ResponseEntity.ok(postService.getSubscribedFeed(authentication))
    }
}
