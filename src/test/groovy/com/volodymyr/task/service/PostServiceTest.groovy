package com.volodymyr.task.service

import com.volodymyr.task.entity.User
import com.volodymyr.task.repository.PostRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Shared
import spock.lang.Specification

class PostServiceTest extends Specification {

    @Shared
    PostService postService
    @Shared
    PostRepository postRepository
    @Shared
    UserService userService

    def setup() {
        postRepository = Mock(PostRepository)
        userService = Mock(UserService)
        postService = new PostService(postRepository, userService)
    }

    def "createPost should create a new post"() {
        given:
        def authentication = new UsernamePasswordAuthenticationToken(new User(), null)
        def text = "Test post"

        when:
        postService.createPost(authentication, text)

        then:
        1 * userService.getAuthenticatedUser(authentication)
        1 * postRepository.save(_)
    }

    def "getPosts should return posts for authenticated user"() {
        given:
        def authentication = new UsernamePasswordAuthenticationToken(new User(), null)
        def userName = null

        when:
        postService.getPosts(authentication, userName)

        then:
        1 * userService.getAuthenticatedUser(authentication)
        1 * postRepository.findAllByOwner(_)
    }

    def "getPosts should return posts for a specific user"() {
        given:
        def authentication = new UsernamePasswordAuthenticationToken(new User(), null)
        def userName = "username"

        when:
        postService.getPosts(authentication, userName)

        then:
        1 * userService.getUser(userName)
        0 * userService.getAuthenticatedUser(authentication)
        1 * postRepository.findAllByOwner(_)
    }
}
