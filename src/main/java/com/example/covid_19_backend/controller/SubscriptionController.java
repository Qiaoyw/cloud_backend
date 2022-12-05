package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.Book;
import com.example.covid_19_backend.pojo.Brief;
import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.service.SubscriptionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("subscription")
@Api(tags = "订阅实时疫情信息")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/add")
    public ResponseData addSubscription(String location) {
        try {
            subscriptionService.addSubscription(location);
            return ResponseData.Success("subscription success", location, 200);
        }catch (Exception e) {
            if (e.getMessage().equals("您未登录")) {
                return ResponseData.Error("用户未登录", null, 401);
            }else{
                return ResponseData.Error(e.getMessage(), null, 402);
            }
        }
    }

    @PostMapping("/cancel")
    public ResponseData cancelSubscription(String location) {
        try {
            subscriptionService.cancelSubscription(location);
            return ResponseData.Success("success", location, 200);
        }catch (Exception e) {
            if (e.getMessage().equals("您未登录")) {
                return ResponseData.Error("用户未登录", null, 401);
            }else{
                return ResponseData.Error(e.getMessage(), null, 402);
            }
        }
    }

    @GetMapping("/detail")
    public ResponseData detailSubscription() {
        try {
            List<Object> result = subscriptionService.getSubscription();
            return ResponseData.Success("success", result, 200);
        }catch (Exception e) {
            return ResponseData.Error(e.getMessage(), null, 401);
        }
    }

    @GetMapping("/size")
    public ResponseData getNotificationSize() {
        try {
            Integer size = subscriptionService.getSize();
            return ResponseData.Success("success", size, 200);
        }catch (Exception e) {
            return ResponseData.Error(e.getMessage(), null, 401);
        }
    }

    @GetMapping("/brief")
    public ResponseData briefSubscription() {
        try {
            List<Brief> briefList = subscriptionService.getBriefSubscription();
            return ResponseData.Success("success", briefList, 200);
        }catch (Exception e) {
            return ResponseData.Error(e.getMessage(), null, 401);
        }
    }

    @GetMapping("/list")
    public ResponseData listSubscription() {
        try {
            List<Book> books = subscriptionService.getListSubscription();
            return ResponseData.Success("success", books, 200);
        }catch (Exception e) {
            return ResponseData.Error(e.getMessage(), null, 401);
        }
    }

    @GetMapping("/province")
    public ResponseData provinceSubscription() {
        try {
            List<Book> books = subscriptionService.getProvinceSubscription();
            return ResponseData.Success("success", books, 200);
        }catch (Exception e) {
            return ResponseData.Error(e.getMessage(), null, 401);
        }
    }

}
