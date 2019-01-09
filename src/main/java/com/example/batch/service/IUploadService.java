package com.example.batch.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: supengfei
 * @Date: 2019/1/9 08:52
 * @Description:
 */
public interface IUploadService {
     String uploadFile(MultipartFile file) throws Exception;
}
