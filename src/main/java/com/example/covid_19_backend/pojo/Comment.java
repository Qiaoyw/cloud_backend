package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("comment")
@AllArgsConstructor
public class Comment {
    @TableId(type = IdType.AUTO)
    Integer id;
    private Integer questionid;
    private String content;
    private String time;
    private String avatar;
    private String username;
    private Integer isauthority;
    private Integer userid;
    public Comment(Integer questionid, String content,String time,String avatar,String username,Integer isauthority,Integer userid){
        this.questionid = questionid;
        this.content = content;
        this.time = time;
        this.avatar = avatar;
        this.username = username;
        this.isauthority = isauthority;
        this.userid = userid;
    }

    public Comment() {

    }
}
