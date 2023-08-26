package com.volodymyr.task.entity

import lombok.Builder
import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "comment")
@Data
class Comment {
    @MongoId
    ObjectId id

    User owner
    String text

    Comment(User owner, String text){
        this.owner = owner
        this.text = text
    }
}
