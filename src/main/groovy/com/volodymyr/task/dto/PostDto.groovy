package com.volodymyr.task.dto

import com.volodymyr.task.entity.Comment
import com.volodymyr.task.entity.Post
import lombok.Data

@Data
class PostDto {

    String id
    String text
    int likesCount
    List<Comment> comments = new LinkedList<>()

    PostDto(Post post){
        this.id = post.getId()
        this.text = post.text
        this.likesCount = post.likesCount
        this.comments = post.comments
    }


}
