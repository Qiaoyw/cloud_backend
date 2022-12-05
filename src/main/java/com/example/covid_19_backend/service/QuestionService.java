package com.example.covid_19_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.covid_19_backend.pojo.Question;
import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;

public interface QuestionService extends IService<Question> {
    //用户提问
    public ResponseData askQuestion(Integer userid, String title, String content) throws Exception;
    //获取问题列表
    public ResponseData getQuestions() throws Exception;

}
