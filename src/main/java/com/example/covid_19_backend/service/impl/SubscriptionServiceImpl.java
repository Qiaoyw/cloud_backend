package com.example.covid_19_backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.covid_19_backend.pojo.*;
import com.example.covid_19_backend.service.SubscriptionService;
import com.example.covid_19_backend.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void addSubscription(String location) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        Query query = new Query(Criteria.where("location").is(location));
        Subscription subscription = mongoTemplate.findOne(query, Subscription.class, "Subscription");
        if (subscription == null) {
            subscription = new Subscription(location, user.getId());
            mongoTemplate.insert(subscription, "Subscription");
        }else {
            subscription.addUser(user.getId());
            Update update = Update.update("user", subscription.getUser());
            mongoTemplate.updateFirst(query, update, "Subscription");
        }
        Notification notification = (Notification) redisUtil.get(location);
        List<Notification> notifications = (List<Notification>) redisUtil.get(String.valueOf(user.getId()));
        if (notifications == null) notifications = new ArrayList<>();
        notifications.add(notification);
        redisUtil.set(String.valueOf(user.getId()), notifications);
    }

    @Override
    public void cancelSubscription(String location) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        Query query = new Query(Criteria.where("location").is(location));
        Subscription subscription = mongoTemplate.findOne(query, Subscription.class, "Subscription");
        if (subscription == null) return;
        subscription.removeUser(user.getId());
        Update update = new Update().set("user", subscription.getUser());
        mongoTemplate.updateFirst(query, update, "Subscription");
    }

    @Override
    public List<Object> getSubscription() throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        Query query = new Query(Criteria.where("user.uid").is(user.getId()));
        List<Subscription> subscriptions = mongoTemplate.find(query, Subscription.class, "Subscription");
        List<Object> details = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            String location = subscription.getLocation();
            Object object = redisUtil.get(location);
            details.add(object);
        }
        return details;
    }

    @Override
    public Integer getSize() throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        Object object = redisUtil.get(String.valueOf(user.getId()));
        if (object == null) return 0;
        List<Notification> notifications = (List<Notification>) object;
        return notifications.size();
    }

    @Override
    public List<Brief> getBriefSubscription() throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        Object object = redisUtil.get(String.valueOf(user.getId()));
        List<Brief> briefs = null;
        if (object != null) {
            List<Notification> notifications = (List<Notification>) object;
            briefs = new ArrayList<>();
            for (Notification notification : notifications) {
                JSONObject jsonObject = notification.getToday();
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                Brief brief = new Brief(notification.getLocation(), jsonObject.get("confirm"), date.format(formatter));
                briefs.add(brief);
            }
        }
        redisUtil.del(String.valueOf(user.getId()));
        return briefs;
    }

    @Override
    public List<Book> getListSubscription() throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        List<String> my_book = new ArrayList<>();
        Query query = new Query(Criteria.where("user.uid").is(user.getId()));
        List<Subscription> subscriptions = mongoTemplate.find(query, Subscription.class, "Subscription");
        for (Subscription subscription : subscriptions) {
            String location = subscription.getLocation();
            my_book.add(location);
        }
        List<Book> books = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) redisUtil.get("China");
        JSONArray array = (JSONArray) jsonArray.getJSONObject(0).get("children");
        for (int i = 0; i < array.size(); ++i) {
            JSONObject object1 = array.getJSONObject(i);
            String province_name = (String) object1.get("name");
            JSONArray child_array = object1.getJSONArray("children");
            for(int j = 0; j < child_array.size(); ++j) {
                JSONObject child_object = child_array.getJSONObject(j);
                String child_name = (String) child_object.get("name");
                Book book;
                if (my_book.contains(child_name)) {
                    book = new Book(province_name, child_name, true);
                }else {
                    book = new Book(province_name, child_name, false);
                }
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> getProvinceSubscription() throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) throw new Exception("您未登录");
        List<String> my_book = new ArrayList<>();
        Query query = new Query(Criteria.where("user.uid").is(user.getId()));
        List<Subscription> subscriptions = mongoTemplate.find(query, Subscription.class, "Subscription");
        for (Subscription subscription : subscriptions) {
            String location = subscription.getLocation();
            my_book.add(location);
        }
        List<Book> books = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) redisUtil.get("China");
        JSONArray array = (JSONArray) jsonArray.getJSONObject(0).get("children");
        for (int i = 0; i < array.size(); ++i) {
            JSONObject object1 = array.getJSONObject(i);
            String province_name = (String) object1.get("name");
            Book book;
            if (my_book.contains(province_name)) {
                book = new Book(province_name, null, true);
            }else {
                book = new Book(province_name, null, false);
            }
            books.add(book);
        }
        return books;
    }
}
