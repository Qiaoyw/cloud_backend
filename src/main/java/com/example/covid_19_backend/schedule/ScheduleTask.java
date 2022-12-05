package com.example.covid_19_backend.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.covid_19_backend.pojo.Notification;
import com.example.covid_19_backend.pojo.SumHistory;
import com.example.covid_19_backend.service.impl.DataServiceImpl;
import com.example.covid_19_backend.tool.NotificationTool;
import com.example.covid_19_backend.util.DataUtil;
import com.example.covid_19_backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScheduleTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private NotificationTool notificationTool;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DataServiceImpl dataService;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void chinaInformationTask() {
        System.out.println("start pull from Internet");
        String All_Province = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
        String province = DataUtil.getChina(All_Province, "province");
        String provinces = province.replaceAll("\\\\", "");
        String result = "";
        int count = 0;
        int len = provinces.length();
        for(int i = 0; i < len; ++i) {
            if (count == 4 || i == len - 2) {
                count++;
                continue;
            }
            if (province.charAt(i) == ':' || provinces.charAt(i) == 'a'){
                count++;
            }
            result += provinces.charAt(i);
        }
        JSONObject json = JSON.parseObject(result);
        Object allProvince = json.get("data");
        JSONObject object = (JSONObject) allProvince;
        allProvince = object.get("areaTree");
        redisUtil.set("China", allProvince);
        System.out.println("pull end");
        JSONArray jsonArray = (JSONArray) allProvince;
        pushNotification(jsonArray.getJSONObject(0), "China", 0);
        JSONArray array = (JSONArray) jsonArray.getJSONObject(0).get("children");
        for (int i = 0; i < array.size(); ++i) {
            JSONObject object1 = array.getJSONObject(i);
            String province_name = (String) object1.get("name");
            pushNotification(object1, province_name, 1);
            JSONArray child_array = object1.getJSONArray("children");
            for(int j = 0; j < child_array.size(); ++j) {
                JSONObject child_object = child_array.getJSONObject(j);
                pushNotification(child_object, province_name, 2);
            }
        }
        System.out.println("233");
    }

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void worldInformationTask() {
        System.out.println("history start");
        List<SumHistory> world = dataService.getWorldHistory();
        redisUtil.set("history_world", world);
        System.out.println("history end");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearRedis() {
        System.out.println("clear");
        redisUtil.clear();
    }

    private void pushNotification(JSONObject jsonObject, String area, int level) {
        String location = (String) jsonObject.get("name");
        JSONObject total = (JSONObject) jsonObject.get("total");
        JSONObject today = (JSONObject) jsonObject.get("today");
        if (level == 2 && location.equals("吉林")) location = "吉林市";
        Notification notification = new Notification(location, total, today, area);
        redisUtil.set(location, notification);
        Object object =redisUtil.get("o-"+location);
        Integer confirm = (Integer) today.get("confirm");
        if (object == null) {
            redisUtil.set("o-"+location,confirm);
            notificationTool.sendMessage(location, total, today, area);
        }
        else {
            Integer old = (Integer) object;
            if (!confirm.equals(old)) {
                redisUtil.set("o-"+location, confirm);
                notificationTool.sendMessage(location, total, today, area);
            }
        }
    }
}
