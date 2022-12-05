package com.example.covid_19_backend.service.impl;

import com.example.covid_19_backend.mapper.TipsMapper;
import com.example.covid_19_backend.pojo.Tips;
import com.example.covid_19_backend.service.TipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipsServiceImpl implements TipsService {
    @Autowired
    private TipsMapper tipsMapper;

    public List<Tips> getAllTips() {
        return tipsMapper.getAllTips();
    }

    public Tips getTipsById(Integer id) {
        return tipsMapper.getTipsById(id);
    }
}
