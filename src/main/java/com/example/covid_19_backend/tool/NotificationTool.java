package com.example.covid_19_backend.tool;

import com.alibaba.fastjson.JSONObject;
import com.example.covid_19_backend.pojo.Country;
import com.example.covid_19_backend.pojo.Notification;
import com.example.covid_19_backend.pojo.Subscription;
import com.example.covid_19_backend.pojo.UserId;
import com.example.covid_19_backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationTool {
    @Autowired
    private MongoTemplate mongoTemplate;

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RedisUtil redisUtil;


    public void sendMessage(Country country) {
        Notification notification = new Notification(country);
        pushAlarm(notification);
    }

    public void sendMessage(String location, JSONObject total, JSONObject today, String province){
        Notification notification = new Notification(location, total, today, province);
        pushAlarm(notification);
    }

    private void pushAlarm(Notification notification) {
        Query query = new Query(Criteria.where("location").is(notification.getLocation()));
        Subscription subscription = mongoTemplate.findOne(query, Subscription.class, "Subscription");
        if (subscription != null) {
            for (UserId userId : subscription.getUser()) {
//                messagingTemplate.convertAndSend("/subscription/" + userId.getUid(), location);
                String uid = String.valueOf(userId.getUid());
                Object object = redisUtil.get(uid);
                System.out.println(notification.getLocation()+"被订阅:" + uid);
                List<Notification> notifications;
                if (object == null) {
                    notifications = new ArrayList<>();
                } else {
                    notifications = (List<Notification>) object;
                    Notification temp = null;
                    for (Notification notification1 : notifications) {
                        if (notification1.getLocation().equals(notification.getLocation())) {
                            temp = notification1;
                            break;
                        }
                    }
                    if (temp != null) {
                        notifications.remove(temp);
                    }
                }
                notifications.add(notification);
                redisUtil.set(uid, notifications);
            }
        }
    }
}
