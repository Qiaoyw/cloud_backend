package com.example.covid_19_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.covid_19_backend.pojo.Comment;
import com.example.covid_19_backend.pojo.Question;
import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;

public interface CommentService extends IService<Comment> {
    //评论问题
    public ResponseData comment(User user, Integer questionid, String content) throws Exception;
    //获取评论列表
    public ResponseData getComments(Integer questionid) throws Exception;
//    //更新评论头像
//    public int updateAvatar(Integer userid,String url)throws Exception;
    //删除评论
    public ResponseData deleteComment(Integer commentId) throws Exception;
}
