package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.CourseInfoVO;
import com.atguigu.serviceedu.entity.vo.CoursePublishVO;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程信息
     * @param courseInfoVO
     * @return
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);


    /**
     * 根据id查询课程
     * @param courseId
     * @return
     */
    CourseInfoVO getCourseInfoById(String courseId);


    /**
     * 更新课程信息
     * @param courseInfoVO
     */
    void updateCourseInfo(CourseInfoVO courseInfoVO);


    /**
     * 确认课程信息
     * @param courseId
     * @return
     */
    CoursePublishVO getCoursePublishVoById(String courseId);


    /**
     * 删除课程
     * @param courseId
     */
    void deleteCourseById(String courseId);

    List<EduCourse> selectFrontCourse();

    Map<String, Object> pageListFront(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    CourseWebVo findCourseDetailInfo(String courseId);
}
