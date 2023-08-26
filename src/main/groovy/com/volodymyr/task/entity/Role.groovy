package com.volodymyr.task.entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority

@Document(collection = "role")
@Data
@NoArgsConstructor

class Role implements GrantedAuthority {

    @Id
    Long id
    String name

    @Override
    String getAuthority() {
        return name
    }
}
