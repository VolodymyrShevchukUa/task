package com.volodymyr.task.repository

import com.volodymyr.task.entity.Comment
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, ObjectId>{

}