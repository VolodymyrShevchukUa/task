package com.volodymyr.task.dto

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class UserRegistration {
    String userName
    String passWord

    UserRegistration(String userName, String passWord){
        this.userName = userName
        this.passWord = passWord
    }
}
