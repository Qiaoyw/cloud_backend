package com.example.covid_19_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.covid_19_backend.mapper.UserMapper;
import com.example.covid_19_backend.pojo.Comment;
import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;
import com.example.covid_19_backend.service.UserService;
import com.example.covid_19_backend.service.impl.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveUpdateOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    //注册
    @Override
    public ResponseData register( String username, String password, String email,String avatar) throws Exception {
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //用户名已存在
            if (userMapper.checkUserName(username).size()!=0)
                return ResponseData.Success("用户名已存在", null, 1);
            queryWrapper.clear();
            // 邮箱已被使用
            if (getOne(queryWrapper.eq("email",email),false)!=null)
                return ResponseData.Success("当前邮箱已被使用", null, 2);

            User user;
            if(email.endsWith("@buaa.edu.cn"))
                user = new User(username, password, email,avatar,1);
            else
                user = new User(username, password, email,avatar,0);
            save(user);
            return ResponseData.Success("注册成功", user, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //登录
    @Override
    public ResponseData login(String email, String password) throws Exception {
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.clear();
            //邮箱未注册
            if (getOne(queryWrapper.eq("email",email),false)==null)
                return ResponseData.Success("邮箱未注册", null, 1);
            queryWrapper.clear();
            //密码错误
            if (getOne(queryWrapper.eq("email",email).eq("password",password),false)==null)
                return ResponseData.Success("密码错误", null, 2);

            //登录成功
            User user;
            user=getOne(queryWrapper.eq("email",email).eq("password",password));
            return ResponseData.Success("登录成功", user, 0);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //登出
    @Override
    public ResponseData logout(HttpSession session)throws Exception{
        try {
            if(session.getAttribute("user") != null){
                session.invalidate();
                return ResponseData.Success("已退出账号", null, 0);
            }
            else
                return ResponseData.Success("用户未登录", null, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //获取登录状态
    @Override
    public ResponseData getStatus(HttpSession session) throws Exception{
        try {
            if(session.getAttribute("user") == null)
                return ResponseData.Success("用户未登录", null, 1);
            else{
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                User user;
                user=getOne(queryWrapper.eq("email",session.getAttribute("user")));
                return ResponseData.Success("用户已登录", user, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //修改密码
    @Override
    public ResponseData updatePassword(Integer id, String oldPassword,String newPassword) throws Exception{
        try{
            //QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //User user = getOne(queryWrapper.eq("id",id));
            User user = getById(id);
//            System.out.println("user"+user);
//            System.out.println("database"+user.getPassword());
//            System.out.println("old"+oldPassword);
            //如果填写的原密码正确
            if(user.getPassword().equals(oldPassword)){
                User updateUser = new User();
                updateUser.setId(id);
                updateUser.setPassword(newPassword);
                if(updateById(updateUser))
                    return ResponseData.Success("修改密码成功", null, 0);
                else return ResponseData.Error("更新错误", null, -1);
            }
            else
                return ResponseData.Error("原密码错误", oldPassword, -1);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //修改邮箱或用户名
    @Override
    public ResponseData updateInformation(Integer id, String username, String email){
        try {
            //未修改信息
            if(getUserById(id).getUsername().equals(username))
                return ResponseData.Success("修改信息成功", null, 0);
            //用户名未重复，可以修改
            if (userMapper.checkUserName(username).size()==0){
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                if(updateById(user))
                    return ResponseData.Success("修改信息成功", null, 0);
                else return ResponseData.Error("更新错误", null, -1);
            }
            else return ResponseData.Error("用户名已被占用",null,-1);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //上传头像
    @Override
    public ResponseData uploadAvatar(Integer id, MultipartFile file) throws Exception {
        try{
            User user = getById(id);
            OssServiceImpl ossServiceImpl = new OssServiceImpl();
            String url = ossServiceImpl.uploadFileAvatar(file);
            user.setAvatar(url);
            if(updateById(user))
            {

                return ResponseData.Success("上传头像成功", user.getAvatar(), 0);
            }
            else return ResponseData.Error("上传错误", null, -1);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return getOne(wrapper);
    }

    @Override

    public User getUserById(Integer userid){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userid);
        return getOne(wrapper);
    }


}
