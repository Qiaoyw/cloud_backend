package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("question")
@AllArgsConstructor
public class Question {
    @TableId(type = IdType.AUTO)
    Integer id;
    private Integer userid;
    private String title;
    private String content;
    private String time;

    public Question(){}
    public Question(Integer userid, String title, String content, String time) {
        this.userid = userid;
        this.content = content;
        this.title = title;
        this.time = time;
    }
    public Question(Integer userid, String title, String content){
        this.userid = userid;
        this.content = content;
        this.title = title;

    }
}
