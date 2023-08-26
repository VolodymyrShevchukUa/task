package com.volodymyr.task.repository

import com.volodymyr.task.entity.Post
import com.volodymyr.task.entity.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, ObjectId>{

    List<Post> findAllByOwner(User owner)

    List<Post> findAllByOwnerIn(List<User> owners)
}