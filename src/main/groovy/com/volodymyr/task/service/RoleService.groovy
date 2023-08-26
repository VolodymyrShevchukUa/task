package com.volodymyr.task.service

import com.volodymyr.task.entity.Role
import com.volodymyr.task.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService {

    @Autowired
    RoleRepository repository

    List<Role> getAllRoles(){
        return repository.findAll()
    }

}
