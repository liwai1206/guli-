package com.atguigu.serviceedu.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/frontindex")
//@CrossOrigin
public class EduFrontIndexController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("getCourseAndTeacher")
    public R getCourseAndTeacher(){

        List<EduCourse> courseList = courseService.selectFrontCourse();


        List<EduTeacher> teacherList = teacherService.selectFrontTeacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);

    }


}
