package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.CourseInfoVO;
import com.atguigu.serviceedu.entity.vo.CoursePublishVO;
import com.atguigu.serviceedu.entity.vo.CourseQuery;
import com.atguigu.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
@RestController
@RequestMapping("/eduservice/educourse")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourseById( @PathVariable String courseId ){
        eduCourseService.deleteCourseById( courseId );
        return R.ok();
    }

    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoVO courseInfoVO){
        String courseId = eduCourseService.saveCourseInfo(courseInfoVO);

        if (StringUtils.isEmpty( courseId )){
            return R.error().message("保存失败");
        }

        return R.ok().data("courseId", courseId);
    }

    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById( @PathVariable String courseId){
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoById(courseId);

        return R.ok().data("courseInfoVO", courseInfoVO);
    }


    @PostMapping("updateCourseInfo")
    public R updateCourseInfo( @RequestBody CourseInfoVO courseInfoVO ){
        eduCourseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    @GetMapping("getCoursePublishVOById/{courseId}")
    public R getCoursePublishVoById(@PathVariable String courseId){
        CoursePublishVO coursePublishVO = eduCourseService.getCoursePublishVoById(courseId);
        return R.ok().data("coursePublish", coursePublishVO);
    }

    @PutMapping("coursePublish/{courseId}")
    public R coursePublish(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId( courseId );
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @PostMapping("getCourseList/{current}/{limit}")
    public R getCourseList(@PathVariable Integer current,
                           @PathVariable Integer limit,
                           @RequestBody CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(current, limit);

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if ( !StringUtils.isEmpty( title )){
            queryWrapper.like("title", title);
        }

        if ( !StringUtils.isEmpty( status )){
            queryWrapper.like("status", status);
        }

        queryWrapper.orderByDesc("gmt_create");

        eduCourseService.page( pageParam, queryWrapper );

        long total = pageParam.getTotal();
        List<EduCourse> records = pageParam.getRecords();

        return R.ok().data("total", total).data("records", records);

    }

}

