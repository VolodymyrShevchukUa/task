package com.volodymyr.task.controller

import com.volodymyr.task.dto.UserRegistration
import com.volodymyr.task.entity.User
import com.volodymyr.task.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @Autowired
    UserService userService

    @GetMapping("/test")
    ResponseEntity<String> testController() {
        userService.getUser("test")
        return ResponseEntity.ok("OKAY")
    }

    @PostMapping(value = "/user-registration")
    ResponseEntity<User> log(@RequestBody UserRegistration userRegistration) {
        return ResponseEntity.ok(userService.userRegistration(userRegistration))
    }

    @PutMapping("/api/change-user")
    ResponseEntity<User> changeUser(@RequestBody UserRegistration userRegistration, Authentication authentication) {
        return ResponseEntity.ok(userService.changeUser(userRegistration, authentication))
    }

    @DeleteMapping("/api/delete-user")
    ResponseEntity<String> deleteUser(Authentication authentication) {
        userService.deleteUser(authentication)
        return ResponseEntity.ok("Deleted")
    }
}
