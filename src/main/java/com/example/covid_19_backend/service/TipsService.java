package com.example.covid_19_backend.service;

import com.example.covid_19_backend.pojo.Tips;

import java.util.List;

public interface TipsService {
    List<Tips> getAllTips();
    Tips getTipsById(Integer id);
}
