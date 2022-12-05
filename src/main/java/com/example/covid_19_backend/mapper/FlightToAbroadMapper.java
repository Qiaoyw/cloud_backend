package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.FlightToAbroad;
import com.example.covid_19_backend.pojo.ProvincePolicy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FlightToAbroadMapper extends BaseMapper<FlightToAbroad>{
    @Select("select * from flightToAbroad")
    public List<FlightToAbroad> getFlightToAbroad();

}
