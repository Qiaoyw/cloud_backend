package com.example.covid_19_backend.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification {
    private String location;
    private JSONObject total;
    private JSONObject today;
    private String province;
    private Country country;

    public Notification(String location, JSONObject total, JSONObject today, String province) {
        this.location = location;
        this.total = total;
        this.today = today;
        this.province = province;
    }

    public Notification(Country country) {
        this.location = country.getName();
        this.country = country;
    }
}
