package com.volodymyr.task.repository

import com.volodymyr.task.entity.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleRepository extends MongoRepository<Role,Long>{

}