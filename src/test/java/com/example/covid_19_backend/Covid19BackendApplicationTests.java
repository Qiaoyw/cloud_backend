package com.example.covid_19_backend;

import com.example.covid_19_backend.pojo.Province;
import com.example.covid_19_backend.pojo.Subscription;
import com.example.covid_19_backend.pojo.SumHistory;
import com.example.covid_19_backend.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class Covid19BackendApplicationTests {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
//        List<Province> provinces = mongoTemplate.findAll(Province.class, "Everyday");
//        for (Province province : provinces) {
//            String regex = "^20-.*$";
//            if (province.getLast_Update().matches(regex) && province.getCountry_Region().equals("China")) {
//                System.out.println(province.getProvince_State() + ":" + province.getLast_Update());
//                String last_update = "20" + province.getLast_Update();
//                Update update = Update.update("Last_Update", last_update);
//                Query query = new Query(Criteria.where("_id").is(province.get_id()));
//                mongoTemplate.updateFirst(query, update, "Everyday");
//            }
//        }
        Query query = new Query(Criteria.where("Country_Region").is("China"));
        List<Province> provinces = mongoTemplate.findAll(Province.class, "Everyday");
        for (Province province : provinces) {
            if (province.getCountry_Region() == null || !province.getCountry_Region().equals("China")) {
//                query = new Query(Criteria.where("_id").is(province.get_id()));
//                mongoTemplate.remove(query, "Everyday");
            }
        }
//        provinces.sort((province, t1) -> {
//            if (province.getLast_Update().equals(t1.getLast_Update())) {
//                return province.getProvince_State().compareTo(t1.getProvince_State());
//            }else {
//                return province.getLast_Update().compareTo(t1.getLast_Update());
//            }
//        });
//        for (int i = 1; i < provinces.size(); ++i) {
//            Province province = provinces.get(i);
//            Province front = provinces.get(i-1);
//            if (province.getProvince_State().equals(front.getProvince_State()) && province.getLast_Update().equals(front.getLast_Update())) {
//                query = new Query(Criteria.where("_id").is(province.get_id()));
//                mongoTemplate.remove(query, "Everyday");
//                System.out.println(province.getProvince_State() + " "  + province.getLast_Update());
//            }
//        }

    }
}
