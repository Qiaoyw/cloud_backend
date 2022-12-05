package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.news;
import com.example.covid_19_backend.service.impl.NewsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("data")
public class NewsController {
    @Autowired
    NewsServiceImpl newsService;

    @GetMapping("/news")
    public ResponseData getNews() {
//        List<news> news = newsService.getAllNews();
//        news.sort((t1, t2) -> t2.getTime().compareTo(t1.getTime()));
        return ResponseData.Success("获取成功", newsService.getNews(), 200);
    }

    @PostMapping("/getNewsById")
    public ResponseData getNewsById(@RequestBody NewsEntity newsEntity) {
        try {
            news news_item = newsService.getNewsById(newsEntity.id);
            return ResponseData.Success("获取成功", news_item, 200);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class NewsEntity{
        private Integer id;
    }
}
