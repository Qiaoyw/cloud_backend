package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.ArrivalFlightsChina;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArrivalFlightsChinaMapper extends BaseMapper<ArrivalFlightsChina>{
    @Select("select * from arrivalFlightsChina")
    public List<ArrivalFlightsChina> getArrivalFlightsChina();

}
