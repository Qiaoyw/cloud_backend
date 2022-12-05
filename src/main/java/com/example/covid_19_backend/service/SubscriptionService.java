package com.example.covid_19_backend.service;


import com.example.covid_19_backend.pojo.Book;
import com.example.covid_19_backend.pojo.Brief;

import java.util.List;

public interface SubscriptionService {
    void addSubscription(String location) throws Exception; //订阅
    void cancelSubscription(String location) throws Exception;//取消订阅
    List<Object> getSubscription() throws Exception;//查看订阅信息
    Integer getSize() throws Exception;
    List<Brief> getBriefSubscription() throws Exception;
    List<Book> getListSubscription() throws Exception;

    List<Book> getProvinceSubscription() throws Exception;
}
