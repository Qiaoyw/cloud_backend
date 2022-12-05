package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.BeginningFlightsChina;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BeginningFlightsChinaMapper extends BaseMapper<BeginningFlightsChina>{
    @Select("select * from beginningFlightsChina")
    public List<BeginningFlightsChina> getBeginningFlightsChina();

}
