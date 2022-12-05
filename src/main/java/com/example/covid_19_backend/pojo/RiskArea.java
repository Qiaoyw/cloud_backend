package com.example.covid_19_backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("risk_area")
public class RiskArea {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String province;
    private String city;
    private String district;
    private String riskLevel;
    private String address;
}
