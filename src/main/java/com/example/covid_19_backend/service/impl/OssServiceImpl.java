package com.example.covid_19_backend.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.covid_19_backend.service.OssService;
import com.example.covid_19_backend.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try{
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            // 1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replace("-","");
            fileName = uuid+fileName;

            // 2把文件按照日期进行分类
            // 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName=datePath+"/"+fileName;

            //调用oss方法实现上传
            ossClient.putObject(bucketName,fileName,inputStream);

            //关闭OSSClient
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接起来
            //  https://violet-imgs.oss-cn-beijing.aliyuncs.com/1.jpg
            String url = "https://"+bucketName+"."+endPoint+'/'+fileName;
            return url;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}