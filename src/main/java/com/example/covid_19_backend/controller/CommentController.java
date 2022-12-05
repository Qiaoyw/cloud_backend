package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;
import com.example.covid_19_backend.service.CommentService;
import com.example.covid_19_backend.service.impl.CommentServiceImpl;
import com.example.covid_19_backend.service.impl.QuestionServiceImpl;
import com.example.covid_19_backend.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"问题相关接口"})
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    //评论问题
    @PostMapping("/comment")
    @ApiOperation("评论问题")
    public ResponseData comment(@RequestBody CommentEntity commentEntity) {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            return commentService.comment(user,Integer.valueOf(commentEntity.getQuestionid()),commentEntity.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }
    //获取评论列表
    @PostMapping("/getcomments")
    @ApiOperation("获取评论列表")
    public ResponseData getcomments(@RequestBody CommentsEntity commentsEntity) {
        try {

            return commentService.getComments(Integer.valueOf(commentsEntity.getQuestionid()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    //删除评论
    @PostMapping("/deletecomment")
    @ApiOperation("删除评论")
    public ResponseData deletecomment(@RequestBody DeleteEntity deleteEntity) {
        try {
            return commentService.deleteComment(Integer.valueOf(deleteEntity.getCommentid()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }


    @Data
    @AllArgsConstructor
    @ApiModel(value = "CommentEntity")
    static class CommentEntity {
        @ApiModelProperty(name = "questionid", value = "问题id", required = true, example = "1")
        private String questionid;
        @ApiModelProperty(name = "content", value = "内容", required = true, example = "example@buaa.edu.cn")
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "CommentsEntity")
    static class CommentsEntity {
        @ApiModelProperty(name = "questionid", value = "问题id", required = true, example = "1")
        private Integer questionid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "DeleteEntity")
    static class DeleteEntity {
        @ApiModelProperty(name = "commentid", value = "评论id", required = true, example = "1")
        private Integer commentid;
    }
}
