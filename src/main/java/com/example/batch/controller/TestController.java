package com.example.batch.controller;

import com.example.batch.entity.Student;
import com.example.batch.util.ExcelUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: supengfei
 * @Date: 2019/1/8 15:00
 * @Description:
 */
@Controller
public class TestController {
    /**
     * @Description 全局变量 每次使用前 先清空  否则会有缓存
     **/
    private List<Student> studentList = new ArrayList<>();

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        String filePath = "C:\\Temp\\" + file.getOriginalFilename();
        //转存文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
         showData(filePath);
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
