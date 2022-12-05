package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.RiskArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RiskAreaMapper extends BaseMapper<RiskArea> {
    @Select("select * from risk_area")
    public List<RiskArea> getAllRiskAreas();

    @Select("select `city` from risk_area")
    public List<String> getAllRiskAreasCity();
}
