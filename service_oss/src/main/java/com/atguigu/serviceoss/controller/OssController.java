package com.atguigu.serviceoss.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceoss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @RequestMapping("/upload")
    public R upload(@RequestParam("file")MultipartFile file){

        String filePath = ossService.upload(file);

        return R.ok().message("文件上传成功").data("url", filePath);
    }

}
