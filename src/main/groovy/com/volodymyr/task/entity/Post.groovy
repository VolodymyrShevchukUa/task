package com.volodymyr.task.entity

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "post")
@Data
class Post {

    @MongoId
    ObjectId id
    User owner
    String text
    int likesCount
    List<Comment> comments = new LinkedList<>()

    Post(User owner, String text){
        this.owner = owner
        this.text = text
    }

    String getId(){
        return id.toString()
    }

}
