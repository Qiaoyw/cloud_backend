package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.Tips;
import com.example.covid_19_backend.service.TipsService;
import com.example.covid_19_backend.service.impl.TipsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data")
public class TipsController {
    @Autowired
    TipsServiceImpl tipsService;

    @GetMapping("/tips")
    public ResponseData getTips() {
        List<Tips> tips = tipsService.getAllTips();
        return ResponseData.Success("获取成功", tips, 200);
    }

    @PostMapping("/getTipsById")
    public ResponseData getTipsById(@RequestBody TipsEntity tipsEntity) {
        try {
            Tips tip = tipsService.getTipsById(tipsEntity.id);
            return ResponseData.Success("获取成功", tip, 200);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TipsEntity{
        private Integer id;
    }
}
