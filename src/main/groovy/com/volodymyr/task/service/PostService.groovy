package com.volodymyr.task.service

import com.volodymyr.task.entity.Comment
import com.volodymyr.task.entity.Post
import com.volodymyr.task.entity.User
import com.volodymyr.task.repository.PostRepository
import io.micrometer.common.util.StringUtils
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import java.nio.file.AccessDeniedException

@Service
class PostService {

    PostRepository postRepository

    UserService userService

    @Autowired
    PostService(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository
        this.userService = userService
    }

    Post createPost(Authentication authentication, String text) {
        Post post = new Post(userService.getAuthenticatedUser(authentication), text)
        postRepository.save(post)
    }

    List<Post> getPosts(Authentication authentication, String userName) {
         List posts = StringUtils.isEmpty(userName)
                ? postRepository.findAllByOwner(userService.getAuthenticatedUser(authentication))
                : postRepository.findAllByOwner(userService.getUser(userName))
        return posts

    }

    boolean deletePost(Authentication authentication, ObjectId postId){
        def post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No such element"))
        if(post.owner == userService.getAuthenticatedUser(authentication)){
            postRepository.delete(post)
        }
        else {
            throw new RuntimeException("Forbidden")
        }
        return true

    }

    Post addComment(Authentication authentication, ObjectId postId, String comment) {
        def post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No such element"))
        post.getComments().add(new Comment(userService.getAuthenticatedUser(authentication), comment))
        return post
    }

    List<Comment> getComments(ObjectId postId){
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No such element")).getComments()
    }

    Post likeOrDislikePost(Authentication authentication, ObjectId postId){
        User user = userService.getAuthenticatedUser(authentication)
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No such element"))
        def posts = user.getLikedPosts()
        if (posts.contains(post)) {
            post.likesCount--
            posts.remove(post)
        }else {
            post.likesCount++
            posts.add(post)
        }
        postRepository.save(post)
        return post
    }

    List<Post> getSubscribedFeed(Authentication authentication){
        def user = userService.getAuthenticatedUser(authentication)
        postRepository.findAllByOwnerIn(userService.getUsersByUsernames(user.getSubscribedThread().toList()))
    }

    Post changePost(Authentication authentication, ObjectId postId, String newComment){
        def post = postRepository.findById(postId).orElseThrow () -> new RuntimeException("No such element")
        def user = userService.getAuthenticatedUser(authentication)
        if(post.owner.id != user.id){
            throw new AccessDeniedException("Access denied")
        }
        post.setText(newComment)
        postRepository.save(post)
        return post
    }

}
