package com.example.covid_19_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.covid_19_backend.mapper.QuestionMapper;
import com.example.covid_19_backend.pojo.*;
import com.example.covid_19_backend.service.QuestionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private CommentServiceImpl commentService;

    @Override
    @RequiresRoles(value={"admin","user"}, logical = Logical.OR)
    public ResponseData askQuestion(Integer userid, String title, String content) throws Exception {
        try {
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            String time = ft.format(new Date());
            Question question = new Question(null,userid,title,content,time);
            save(question);
            return ResponseData.Success("提问成功", question, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }
    @Override
    public ResponseData getQuestions() {
        try {
            QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
            List<Question> questionList = list(queryWrapper.orderByDesc("time"));
            if (questionList.size()==0) {
                return ResponseData.Success("目前没有问题！", null, 1);
            }else{
                List<QuestionDTO> questionDTOS = new ArrayList<>();
                for (Question question : questionList) {
                    Integer size = commentService.getSize(question.getId());
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(question, questionDTO);
                    questionDTO.setCommentSize(size);
                    questionDTOS.add(questionDTO);
                }
                return ResponseData.Success("获取问题列表成功", questionDTOS, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }


}
