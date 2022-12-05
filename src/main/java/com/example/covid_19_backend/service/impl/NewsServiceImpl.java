package com.example.covid_19_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.covid_19_backend.mapper.NewsMapper;
import com.example.covid_19_backend.mapper.TipsMapper;
import com.example.covid_19_backend.pojo.Tips;
import com.example.covid_19_backend.pojo.news;
import com.example.covid_19_backend.service.NewsService;
import com.example.covid_19_backend.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    public List<news> getAllNews() {
        return newsMapper.getAllNews();
    }

    @Override
    public news getNewsById(Integer id) {
        return newsMapper.getNewsById(id);
    }

    public Object getNews() {
        String news = "http://dzq.wenlvnews.com/index.php/yiqing/news";
        String now_new = DataUtil.getChina(news, null);
        JSONObject json = JSON.parseObject(now_new);
        Object object = json.get("con");
        return object;
    }

}
