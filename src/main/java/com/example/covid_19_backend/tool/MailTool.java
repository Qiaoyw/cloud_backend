package com.example.covid_19_backend.tool;

import org.apache.commons.mail.HtmlEmail;

public class MailTool {

    public static boolean send(String emailaddress, String message ) {
        // 准备email
        HtmlEmail email = new HtmlEmail();
        try {
            //填写要发送的email信息
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName("smtp.163.com");
            // 字符编码集的设置
            email.setCharset("UTF-8");
            // 收件人的邮箱
            email.addTo(emailaddress);
            // 发送人的邮箱
//            email.setFrom("rootxmg@163.com", "root");
            email.setFrom("15680788682@163.com", "邓伦圈外小女友");
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication("15680788682@163.com", "ngdwqqngjshmp7");
            // 要发送的邮件主题
            email.setSubject("这是一个java验证码demo");
            // 要发送的信息，可以使用了HtmlEmail，实现邮件内容中使用HTML标签
            email.setMsg("本次的验证吗是："+message);

            email.send();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}