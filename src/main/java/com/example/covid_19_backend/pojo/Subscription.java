package com.example.covid_19_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private String location;
    private List<UserId> user;
    public Subscription(String location, Integer userId) {
        this.location = location;
        user = new ArrayList<>();
        user.add(new UserId(userId));
    }

    public void addUser(Integer userId) throws Exception {
        for (UserId id : user) {
            if (id.getUid().equals(userId)) {
                throw new Exception("您已订阅该地区");
            }
        }
        user.add(new UserId(userId));
    }

    public void removeUser(Integer userId) throws Exception {
        UserId temp = null;
        for (UserId userDTO : user) {
            if (userDTO.getUid().equals(userId)) {
                temp = userDTO;
                break;
            }
        }
        if (temp != null) user.remove(temp);
        else throw new Exception("您未订阅该地区");
    }
}
