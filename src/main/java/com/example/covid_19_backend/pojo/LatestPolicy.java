package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("latest_policy")
public class LatestPolicy {
    private Integer id;
    private String title;
    private String time;
    private String pageUrl;
}
