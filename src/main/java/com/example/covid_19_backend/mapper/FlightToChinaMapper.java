package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.FlightToAbroad;
import com.example.covid_19_backend.pojo.FlightToChina;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FlightToChinaMapper extends BaseMapper<FlightToChina> {
    @Select("select * from flightToChina")
    public List<FlightToChina> getFlightToChina();
}
