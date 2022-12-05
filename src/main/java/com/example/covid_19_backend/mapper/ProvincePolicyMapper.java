package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.ProvincePolicy;
import com.example.covid_19_backend.pojo.Question;
import com.example.covid_19_backend.pojo.Tips;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProvincePolicyMapper extends BaseMapper<Question> {
    @Select("select * from province_policy")
    public List<ProvincePolicy> getAllProvincePolicies();

    @Select("select * from province_policy where id = #{id}")
    public ProvincePolicy getProvincePolicyById(Integer id);
}

