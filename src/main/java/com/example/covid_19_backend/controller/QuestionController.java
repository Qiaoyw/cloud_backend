package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;
import com.example.covid_19_backend.service.impl.QuestionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"问题相关接口"})
public class QuestionController {
    @Autowired
    private QuestionServiceImpl questionService;

    // @ApiParam(required = "是否必须参数", name = "参数名称", value = "参数具体描述")
    //提问
    @PostMapping("/ask")
    @ApiOperation("提问") // @ApiOperation(value = "接口说明", httpMethod = "接口请求方式", response = "接口返回参数类型", notes = "接口发布说明")
    public ResponseData ask(@RequestBody  QuestionEntity questionEntity) {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            return questionService.askQuestion(user.getId(),questionEntity.getTitle(),questionEntity.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //获取问题列表
    @GetMapping("/getquestions")
    @ApiOperation("获取问题列表")
    public ResponseData getquestions() {
        try {
            return questionService.getQuestions();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }



    @Data
    @AllArgsConstructor
    @ApiModel(value = "QuestionEntity") // 用于返回对象, 描述返回对象的意义
    static class QuestionEntity {
        /**
         * @ApiModelProperty 用于方法、字段; 表示对model属性的说明或者数据操作更改
         * value–字段说明
         * name–重写属性名字
         * dataType–重写属性类型
         * required–是否必填
         * example–举例说明
         * hidden–隐藏
         */
        @ApiModelProperty(name = "title", value = "标题", required = true, example = "buaa")
        private String title;
        @ApiModelProperty(name = "content", value = "内容", required = true, example = "example@buaa.edu.cn")
        private String content;
    }

}
