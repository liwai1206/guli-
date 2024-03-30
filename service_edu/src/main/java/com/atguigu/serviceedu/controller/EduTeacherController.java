package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-06-14
 */
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/findAll")
    public R findAllEduTeacher(){
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return R.ok().data("items", eduTeacherList);
    }


    @DeleteMapping("{id}")
    public R deleteById(@PathVariable("id") String id){
        boolean flag = eduTeacherService.removeById(id);
        return flag? R.ok() : R.error();
    }


    @GetMapping("pageList/{current}/{limit}")
    public R pageList( @PathVariable("current") Long current,
                       @PathVariable("limit") Long limit){

        Page<EduTeacher> pageParam = new Page<>(current, limit);

        eduTeacherService.page( pageParam, null );

        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();

        return R.ok().data("total", total).data("records", records);

    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") Long current,
                               @PathVariable("limit") Long limit,
                               @RequestBody TeacherQuery teacherQuery){

        Page<EduTeacher> pageParam = new Page<>(current, limit);

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if ( !StringUtils.isEmpty( name )){
            queryWrapper.like("name", name);
        }

        if ( !StringUtils.isEmpty( level )){
            queryWrapper.eq("level", level);
        }

        if ( !StringUtils.isEmpty( begin )){
            queryWrapper.ge("gmt_create", begin);
        }

        if ( !StringUtils.isEmpty( end )){
            queryWrapper.le("gmt_create", end);
        }

        queryWrapper.orderByDesc("gmt_create");

        eduTeacherService.page( pageParam, queryWrapper );

        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();

        return R.ok().data("total", total).data("records", records);

    }

    @PostMapping("saveTeacher")
    public R saveTeacher( @RequestBody EduTeacher eduTeacher){
        boolean result = eduTeacherService.save(eduTeacher);
        return result? R.ok(): R.error();
    }


    @GetMapping("getTeacherById/{id}")
    public R getTeacherById( @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
//        try {
//            int i = 1/0;
//        } catch (Exception e) {
//            throw new GuliException(5000, "自定义异常");
//        }
        return R.ok().data("teacher", eduTeacher);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean result = eduTeacherService.updateById(eduTeacher);
        return result? R.ok(): R.error();
    }




}

