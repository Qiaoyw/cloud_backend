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
@TableName("province_policy")
public class ProvincePolicy {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String province;
    private String content;
}
