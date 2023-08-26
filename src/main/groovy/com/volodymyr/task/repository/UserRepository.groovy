package com.volodymyr.task.repository

import com.volodymyr.task.entity.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, ObjectId> {

    @Override
    List<User> findAll() ;

    Optional<User> findByUsername(String userName);

    List<User> findAllByUsernameIn(List<User> usernamse)
}