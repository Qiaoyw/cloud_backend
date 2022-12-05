package com.example.covid_19_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class news {
    private Integer id;
    private String title;
    private String time;
    private String content;
}
