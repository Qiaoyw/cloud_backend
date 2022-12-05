package com.example.covid_19_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {

    //注册
        /**
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public ResponseData register(String username, String password, String email, String avatar) throws Exception;

    //登录
    /**
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public ResponseData login(String email, String password) throws Exception;

    //登出
    /**
     * @return
     * @throws Exception
     */
    public ResponseData logout(HttpSession session) throws Exception;

    //获取登录状态
    /**
     * @return
     * @throws Exception
     */
    public ResponseData getStatus(HttpSession session) throws Exception;

    //修改密码
    /**
     * @return
     * @throws Exception
     */
    public ResponseData updatePassword(Integer id, String oldPassword,String newPassword) throws Exception;

    //修改邮箱或用户名
    /**
     * @return
     * @throws Exception
     */
    public ResponseData updateInformation(Integer id, String username, String email) throws Exception;

    //上传头像
    /**
     * @return
     * @throws Exception
     */
    public ResponseData uploadAvatar(Integer id, MultipartFile file) throws Exception;

    User getUserByEmail(String email);

    User getUserById(Integer userid);
}
