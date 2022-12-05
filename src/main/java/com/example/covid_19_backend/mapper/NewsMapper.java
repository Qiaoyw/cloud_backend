package com.example.covid_19_backend.mapper;
import com.example.covid_19_backend.pojo.Tips;
import com.example.covid_19_backend.pojo.news;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsMapper {
    @Select("select * from news")
    public List<news> getAllNews();

    @Select("select * from news where id = #{id}")
    public news getNewsById(Integer id);
}
