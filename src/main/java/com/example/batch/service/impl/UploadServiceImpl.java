package com.example.batch.service.impl;

import com.example.batch.service.IUploadService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: supengfei
 * @Date: 2019/1/9 08:53
 * @Description:
 */
@Service
public class UploadServiceImpl implements IUploadService {
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String targetPath = "C:\\Temp";
        File target = new File(targetPath);
        if(!target.exists()){
            target.mkdirs();
        }
        target.setWritable(true);
        //原文件名
        String name = file.getOriginalFilename();
        //文件大小  单位:kb
        //String size = file.getSize()/1024+"kb";
        // 文件类型  后缀名
        String type = name.substring(name.lastIndexOf(".")+1);
        if (!type.equals("xls") && !type.equals("xlsx")) {
            return "文件格式不正确!";
        }
        //新文件名
        String newName = UUID.randomUUID().toString().replace("-", "")+"."+type;
        //根目录
        String path =   targetPath + File.separator + newName;
        System.out.println(targetPath + File.separator + path);
        //创建保持上传文件的File对象
        File destFile = new File(path);
        //调用MultipartFile对象中复制文件的方法，将上传文件传输指定的位置
        file.transferTo(destFile);

        return path;
    }
}
