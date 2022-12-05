package com.example.covid_19_backend.service;

import com.example.covid_19_backend.pojo.news;

import java.util.List;

public interface NewsService {
    List<news> getAllNews();

    news getNewsById(Integer id);
}
