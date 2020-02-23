package com.richard.project.conroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @author: richard
 *  @Date: 2020/2/23 16:55
 *  @Description:
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

}
