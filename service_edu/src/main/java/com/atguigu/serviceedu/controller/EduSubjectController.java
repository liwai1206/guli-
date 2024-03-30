package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-06-21
 */
@RestController
@RequestMapping("/eduservice/edusubject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;


    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

        eduSubjectService.importSubjectData( file, eduSubjectService);

        return R.ok();
    }


    @GetMapping("getAllOneSubject")
    public R getAllOneSubject(){

        List<OneSubject> list =  eduSubjectService.findAllOneSubject();

        return R.ok().data("list", list);
    }

}

