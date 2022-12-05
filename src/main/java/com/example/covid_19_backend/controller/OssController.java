package com.example.covid_19_backend.controller;

import com.example.covid_19_backend.pojo.ResponseData;
import com.example.covid_19_backend.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

//    @PostMapping("/upload")
//    public ResponseData uploadOssFile(MultipartFile file){
//        String url = ossService.uploadFileAvatar(file);
//        return ResponseData.Success("上传成功",url,0);
//    }

}
