package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.FlightToChina;
import com.example.covid_19_backend.pojo.Railway;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RailwayMapper extends BaseMapper<Railway> {
    @Select("select * from railway")
    public List<Railway> getRailway();
}
