package com.example.batch.controller;

import com.example.batch.entity.Student;
import com.example.batch.service.IUploadService;
import com.example.batch.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: supengfei
 * @Date: 2019/1/8 15:00
 * @Description:
 */
@Controller
public class TestController {

    @Autowired
    private IUploadService uploadService;
    /**
     * @Description 全局变量 每次使用前 先清空  否则会有缓存
     **/
    private List<Student> studentList = new ArrayList<>();

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        //上传文件到指定位置
        String uploadFile = uploadService.uploadFile(file);
        //读取文件
        showData(uploadFile);
        return;
    }

    protected List<Student> showData(String filePath) throws Exception {
        File file = new File(filePath);
        List<String[]> list = ExcelUtil.readExcel(file);
        Student student;
        //清空
        studentList.clear();
        for (int i=0;i<list.size();i++) {
            //注意对应
            student = new Student();
            student.setName(list.get(i)[0]);
            student.setStuNo(list.get(i)[1]);
            student.setSex(list.get(i)[2]);
            student.setAge(Integer.parseInt(list.get(i)[3]));
            studentList.add(student);
        }
        return studentList;
    }

    @RequestMapping("/openAdd")
    public String openAdd(){
        return "add.html";
    }

    @RequestMapping("/showData")
    @ResponseBody
    public Object showData(){
        return studentList;
    }
}
