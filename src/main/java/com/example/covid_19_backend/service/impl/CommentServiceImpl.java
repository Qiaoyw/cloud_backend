package com.example.covid_19_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.covid_19_backend.mapper.CommentMapper;
import com.example.covid_19_backend.mapper.QuestionMapper;
import com.example.covid_19_backend.pojo.Comment;
import com.example.covid_19_backend.pojo.Question;
import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.pojo.User;
import com.example.covid_19_backend.service.CommentService;
import com.example.covid_19_backend.service.QuestionService;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    @RequiresRoles(value={"admin","user"}, logical = Logical.OR)
    public ResponseData comment(User user, Integer questionid, String content) throws Exception{
        try{
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            String time = ft.format(new Date());
            Comment comment = new Comment(questionid,content,time,user.getAvatar(),user.getUsername(),user.getIsauthority(),user.getId());
            save(comment);
            return ResponseData.Success("评论成功", comment, 0);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    public ResponseData getComments(Integer questionid) throws Exception{
        try {
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            List<Comment> commentList = list(queryWrapper.eq("questionid",questionid).orderByDesc("isauthority","time"));

            //更新头像和用户名
            ListIterator<Comment> listIterator = commentList.listIterator();
            while (listIterator.hasNext()){
                Comment comment = listIterator.next();
                Integer userid = comment.getUserid();
                User user = userService.getUserById(userid);
                if(user.getAvatar()!=null){
                    String avatar = user.getAvatar();
                    comment.setAvatar(avatar);

                }
                comment.setUsername(user.getUsername());
            }




            if (commentList.size()==0)
                return ResponseData.Success("目前没有评论！", null, 1);
            return ResponseData.Success("获取评论列表成功", commentList, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    @Override
    //删除评论
    public ResponseData deleteComment(Integer commentId) throws Exception{
        try {
            removeById(commentId);
            return ResponseData.Success("评论已删除", null, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.Error("错误", null, -1);
        }
    }

    public Integer getSize(Integer questionid){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        List<Comment> commentList = list(queryWrapper.eq("questionid",questionid));
        return commentList.size();
    }
}
