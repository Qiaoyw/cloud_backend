package com.example.covid_19_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.covid_19_backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
//  // 添加用户
//  int addUser(User user);
//
    // 查询重名情况
    @Select("select * from user where binary username = #{username};")
    public List<User> checkUserName(String userName);
//
//  // 用户登录 - 返回UserID
//  String login(String phoneNumber, String password);
//
//  // 用户信息
//  UserInfo info(String userID);
//
//  // 用户信息 by 联系方式
//  UserInfo infoByPhoneNumber(String phoneNumber);
//
//  // 修改联系方式
//  int changeTelephone(String oldPhone, String newPhone);
//
//  // 查看联系方式是否已被使用
//  UserInfo telephoneIsExist(String phoneNumber);
}
