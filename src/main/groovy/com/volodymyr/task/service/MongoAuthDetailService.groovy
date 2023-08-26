package com.volodymyr.task.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.volodymyr.task.entity.Role
import com.volodymyr.task.entity.User
import com.volodymyr.task.repository.RoleRepository
import com.volodymyr.task.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MongoAuthDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository
    @Autowired
    RoleRepository repository

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new)
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>()
        Collection<GrantedAuthority> authorities = user.getAuthorities()
        authorities.forEach(role -> {grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()))})
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities)
    }
}
