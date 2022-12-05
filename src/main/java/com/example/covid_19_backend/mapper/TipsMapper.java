package com.example.covid_19_backend.mapper;
import com.example.covid_19_backend.pojo.Tips;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TipsMapper {
    @Select("select * from tips")
    public List<Tips> getAllTips();

    @Select("select * from tips where id = #{id}")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Tips getTipsById(Integer id);
}
