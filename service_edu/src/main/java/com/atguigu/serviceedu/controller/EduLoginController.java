package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @RequestMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }


    @RequestMapping("info")
    public R getInfo(){
        return R.ok().data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
