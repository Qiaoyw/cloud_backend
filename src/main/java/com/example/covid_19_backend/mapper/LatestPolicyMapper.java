package com.example.covid_19_backend.mapper;
        import com.example.covid_19_backend.pojo.LatestPolicy;
        import com.example.covid_19_backend.pojo.ProvincePolicy;
        import com.example.covid_19_backend.pojo.news;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Select;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Mapper
@Repository
public interface LatestPolicyMapper {
    @Select("select * from latest_policy")
    public List<LatestPolicy> getAllLatestPolicy();

    @Select("select * from latest_policy where id = #{id}")
    public LatestPolicy getLatestPolicyById(Integer id);
}
