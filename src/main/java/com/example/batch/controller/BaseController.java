package com.example.batch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: supengfei
 * @Date: 2019/1/8 14:35
 * @Description:
 */
@Controller
public class BaseController {
    @RequestMapping("/")
    public String index(){
        return "index.html";
    }
}
