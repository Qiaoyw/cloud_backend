package com.example.covid_19_backend.pojo;

import lombok.Data;

@Data
public class QuestionDTO {
    Integer id;
    private Integer userid;
    private String title;
    private String content;
    private String time;
    private Integer commentSize;
}
