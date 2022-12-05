package com.example.covid_19_backend.controller;


import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;
import com.example.covid_19_backend.service.impl.CommentServiceImpl;
import com.example.covid_19_backend.service.impl.UserServiceImpl;
import com.example.covid_19_backend.util.JWTUtil;
import com.example.covid_19_backend.util.RedisUtil;
import com.example.covid_19_backend.util.VerCodeGenerateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = {"用户相关接口"})
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private RedisUtil redisUtil;

    //注册
    @PostMapping("/register")
    @ApiOperation("注册")
    public ResponseData register(@RequestBody RegisterEntity registerEntity) {
      try {
          return userService.register(registerEntity.getUsername(), registerEntity.getPassword(), registerEntity.getEmail(),registerEntity.getAvatar());
	  } catch (Exception e) {
          e.printStackTrace();
	     return ResponseData.Error("错误", null, -1);
	  }
    }

    //登录
    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseData login(@RequestBody LoginEntity loginEntity) {
        try {
            ResponseData login = userService.login(loginEntity.getEmail(), loginEntity.getPassword());
            if (login.getData() != null) {
                Map<String, Object> resMap = new HashMap<>();
                long currentTimeMillis = System.currentTimeMillis();
                User user = (User)login.getData();
                String token = JWTUtil.createToken(user.getEmail(), currentTimeMillis);
                redisUtil.set(user.getEmail(), currentTimeMillis, 7 * 24 * 60 * 60);
                resMap.put("user", user);
                resMap.put("token", token);
                login.setData(resMap);
            }
            return login;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //登出
    @DeleteMapping("/logout")
    @ApiOperation("登出")
    @RequiresRoles("user")
    public ResponseData logout() {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            redisUtil.del(user.getEmail());
            return ResponseData.Error("登出成功！", null, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //获取登录状态
    @GetMapping("/getstatus")
    @ApiOperation("获取登录状态")
    public ResponseData getStatus(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(false);
            if(session==null)
                return ResponseData.Success("用户未登录", null, 1);
            else return userService.getStatus(session);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //修改密码
    @PutMapping("/password")
    @ApiOperation("修改密码")
    @RequiresRoles("user")
    public ResponseData updatePassword(@RequestBody PasswordEntity passwordEntity) {
        try{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            return userService.updatePassword(user.getId(), passwordEntity.oldPassword, passwordEntity.newPassword);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //修改邮箱或用户名
    @PutMapping("/information")
    @ApiOperation("修改信息")
    @RequiresRoles("user")
    public ResponseData updateInformation(@RequestBody InfoEntity infoEntity){
        try{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            return userService.updateInformation(user.getId(), infoEntity.username, infoEntity.email);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //上传头像
    @PutMapping("/upload")
    public ResponseData uploadAvatar(Integer id,MultipartFile file){
        try{
//            OssServiceImpl ossServiceImpl = new OssServiceImpl();
//            String url = ossServiceImpl.uploadFileAvatar(file);
//            commentService.updateAvatar(id,url);
            return userService.uploadAvatar(id,file);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //获取登录邮件验证码
    @PostMapping ("/sendEmail")
    @ApiOperation("获取登录邮件验证码")
    @ResponseBody
    public ResponseData sendEmail(@RequestBody VerCodeEntity verCodeEntity, HttpServletRequest request){
        String email = verCodeEntity.getEmail();
        HttpSession session = request.getSession();
        //获取验证码
        String verCode = VerCodeGenerateUtil.getVerCode();
//        System.out.println(verCode);
        //发送时间 --这里自己写了一个时间类，格式转换，用于邮件发送
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String time = ft.format(new Date());
        Map<String,String> map = new HashMap<>();
        map.put("code",verCode);
        map.put("email",email);
//        //验证码和邮箱，一起放入session中
//        session.setAttribute("verCode",map);
//        Map<String,String> codeMap = (Map<String,String>) session.getAttribute("verCode");
//        //创建计时线程池，到时间自动移除验证码
//        try {
//            scheduledExecutorService.schedule(new Thread(()->{
//                if(email.equals(codeMap.get("email"))){
//                    session.removeAttribute("verCode");
//                }
//            }), 5*60, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            HtmlEmail htmlemail = new HtmlEmail();
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            htmlemail.setHostName("smtp.163.com");
            // 字符编码集的设置
            htmlemail.setCharset("UTF-8");
            // 收件人的邮箱
            htmlemail.addTo(email);
            // 发送人的邮箱
            htmlemail.setFrom("zyy20001008@163.com", "COVID_19");
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            htmlemail.setAuthentication("zyy20001008@163.com", "XPTLZDPKAWWHTSLI");
            // 要发送的邮件主题
            htmlemail.setSubject("【covid_19】 注册账号验证码");
            // 要发送的信息，可以使用了HtmlEmail，实现邮件内容中使用HTML标签
            htmlemail.setMsg("<h3>\n" +
                    "\t<span style=\"font-size:16px;\">亲爱的用户：</span> \n" +
                    "</h3>\n" +
                    "<p>\n" +
                    "\t<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:14px;\">&nbsp; <span style=\"font-size:16px;\">&nbsp;&nbsp;您好！您正在进行邮箱验证，本次请求的验证码为：<span style=\"font-size:24px;color:#6495ED;\"> "+verCode+"</span>,本验证码5分钟内有效，请在5分钟内完成验证。（请勿泄露此验证码）如非本人操作，请忽略该邮件。(这是一封自动发送的邮件，请不要直接回复）</span></span>\n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:16px;color:#000000;\"><span style=\"color:#000000;font-size:16px;background-color:#FFFFFF;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;background-color:#FFFFFF;\">COVID_19数据分享平台</span></span></span> \n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:14px;\"><span style=\"color:#FF9900;font-size:18px;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;\"><span style=\"font-size:16px;color:#000000;background-color:#FFFFFF;\">"+time+"</span><span style=\"font-size:18px;color:#000000;background-color:#FFFFFF;\"></span></span></span></span> \n" +
                    "</p>");

            try {
                //发送邮件
                htmlemail.send();
            } catch (MailException e) {
                //邮箱是无效的，或者发送失败
                return ResponseData.Error("邮箱是无效的，或者发送失败", null, 1);
            }
        } catch (  EmailException e) {
            //发送失败--服务器繁忙
            ResponseData error = ResponseData.Error("发送失败--服务器繁忙", null, 2);
            return error;
        }
        //发送验证码成功
        return ResponseData.Success("验证码已发送", verCode, 0);
    }

    //根据id获取用户信息
    @PostMapping ("/userInfo")
    @ApiOperation("根据id获取用户信息")
    @ResponseBody
    public ResponseData sendEmail(@RequestBody userInfoEntity userInfoEntity){
        try {
            User user = userService.getUserById(userInfoEntity.userid);
            return ResponseData.Success("获取用户信息成功", user, 0);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }



    @Data
    @AllArgsConstructor
    @ApiModel(value = "LoginEntity")
    static class LoginEntity {
        @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "example@buaa.edu.cn")
        private String email;
        @ApiModelProperty(name = "password", value = "密码", required = true, example = "&shieshuyuan21")
        private String password;
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "PasswordEntity")
    static class PasswordEntity {
        @ApiModelProperty(name = "oldPassword", value = "旧密码", required = true, example = "&shieshuyuan21")
        private String oldPassword;
        @ApiModelProperty(name = "newPassword", value = "新密码", required = true, example = "a123456")
        private String newPassword;
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "InfoEntity")
    static class InfoEntity {
        @ApiModelProperty(name = "name", value = "用户名", required = true, example = "buaa")
        private String username;
        @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "example@buaa.edu.cn")
        private String email;
    }

    @Data
    @AllArgsConstructor
    @ApiModel(value = "RegisterEntity")
    static class RegisterEntity {
        @ApiModelProperty(name = "name", value = "用户名", required = true, example = "buaa")
        private String username;
        @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "example@buaa.edu.cn")
        private String email;
        @ApiModelProperty(name = "password", value = "密码", required = true, example = "&shieshuyuan21")
        private String password;
        @ApiModelProperty(name = "vercode", value = "验证码", required = true, example = "HIEM")
        private String vercode;
        @ApiModelProperty(name = "avatar", value = "头像", required = true, example = "https://buaa-software-covid-19.oss-cn-beijing.aliyuncs.com/2021/07/05/ef01ca1d7ca04cb7bb855d8614933a6fyjt.jpg")
        private String avatar;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "VerCodeEntity")
    static class VerCodeEntity {
        @ApiModelProperty(name = "email", value = "邮箱", required = true, example = "example@buaa.edu.cn")
        private String email;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "userInfoEntity")
    static class userInfoEntity {
        @ApiModelProperty(name = "userid", value = "userid", required = true, example = "1")
        private Integer userid;
    }
}
