package com.atguigu.serviceedu.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
@RestController
public class EduFrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    // 分页查询讲师的前台接口
    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable long page,
                      @PathVariable long limit){

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb( pageParam );

        System.out.println("123456");

        return R.ok().data( map );
    }

    @GetMapping("getTeacherFrontDetail/{teacherId}")
    public R getTeacherFrontDetail( @PathVariable String teacherId){
        // 根据teacherId查询teacher信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 根据teacherId查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId );
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }

}
