package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("user")
@AllArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    Integer id;
    String username;
    String email;
    String password;
    String avatar;
    Integer isauthority;

    public User() {
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId(){
        return id;
    }

    public String getAvatar(){
        return avatar;
    }

    public User(String userName, String password, String emailAddress,String avatar, Integer isauthority) {
        this.username = userName;
        this.email = emailAddress;
        this.password = password;
        this.isauthority = isauthority;
        this.avatar = avatar;
    }


}
