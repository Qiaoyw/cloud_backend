package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.service.impl.DataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("travel")
public class AirlineController {
    @Autowired
    DataServiceImpl dataService;

    @GetMapping("/airline")
    public ResponseData getAirLine(){
        List<String> allRiskAreasCity = dataService.getAllRiskAreasCity();
        return ResponseData.Success("获取成功", allRiskAreasCity, 200);
    }
}
