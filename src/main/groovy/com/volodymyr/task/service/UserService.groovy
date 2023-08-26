package com.volodymyr.task.service

import com.volodymyr.task.dto.UserRegistration
import com.volodymyr.task.entity.User
import com.volodymyr.task.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {


    UserRepository userRepository

    PasswordEncoder passwordEncoder

    RoleService roleService

    @Autowired
    UserService (UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService){
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
        this.roleService = roleService
    }


    User userRegistration(UserRegistration userRegistration) {
        User user = new User()
        def existingUser = userRepository.findByUsername(userRegistration.getUserName())
        if (existingUser?.isEmpty()) {
            user.setUsername(userRegistration.getUserName())
            user.setPassword(passwordEncoder.encode(userRegistration.getPassWord()))
            user.setRoles(new LinkedHashSet<>(roleService.getAllRoles()))
            userRepository.save(user)
        } else {
            throw new RuntimeException("User already exist")
        }
        return user
    }

// I know about transactional
    User changeUser(UserRegistration userRegistration, Authentication authentication) {
        def authUser = getAuthenticatedUser(authentication)
        User user = userRepository.findByUsername(userRegistration.getUserName())
                .orElseThrow(() -> new RuntimeException("There are no user"))
        if(user.id != authUser.id){
            throw new RuntimeException("Forbidden")
        }
        user.setPassword(passwordEncoder.encode(userRegistration.getPassWord()))
        userRepository.save(user)
        return user
    }

    User getUser(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("There are no user"))
    }

    List<User> getUsersByUsernames(List<String> usernames) {
        return userRepository.findAllByUsernameIn(usernames)
    }

    User subscribeOnOff(Authentication authentication, String username) {
        def user = getAuthenticatedUser(authentication)
        def thread = user.getSubscribedThread()
        if (thread.contains(username)) {
            thread.remove(username)
        } else {
            thread.add(username)
        }
        return user
    }

    boolean deleteUser(Authentication authentication){
        def user = getAuthenticatedUser(authentication)
        userRepository.delete(user)
    }

    User getAuthenticatedUser(Authentication authentication){
        def details = (UserDetails) authentication.getPrincipal()
        return getUser(details.getUsername())
    }


}
