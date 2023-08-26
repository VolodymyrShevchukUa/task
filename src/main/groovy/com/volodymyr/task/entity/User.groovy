package com.volodymyr.task.entity


import lombok.Data
import lombok.EqualsAndHashCode
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "user")
@Data
class User implements UserDetails {

    @MongoId
    ObjectId id
    String username
    String password
    Set<Role> roles
    Set<Post> likedPosts = new HashSet<>()
    Set<String> subscribedThread

    User(){}

    User(String username, String password, Set<Role> roles){
        this.username = username
        this.password = password
        this.roles = roles
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
    }

    @Override
    String getUsername() {
        return username
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}
