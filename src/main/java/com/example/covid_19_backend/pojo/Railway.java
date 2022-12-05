package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("railway")
public class Railway {
    private Integer id;
    private String number;
    private String beginning;
    private String arrival;
    private String beginTime;
    private String arriveTime;
    private String totalTime;
    private String distance;
}
