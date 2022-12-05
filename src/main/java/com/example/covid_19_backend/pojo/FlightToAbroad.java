package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("flightToAbroad")
public class FlightToAbroad {
    private Integer id;
    private String number;
    private String beginning;
    private String arrival;
    private String terminal;
    private String scheduledTime;
    private String actualTime;
    private String status;
}
