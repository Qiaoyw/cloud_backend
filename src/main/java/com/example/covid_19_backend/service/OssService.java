package com.example.covid_19_backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    String uploadFileAvatar(MultipartFile file);
}
