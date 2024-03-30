package com.atguigu.serviceedu.mapper;

import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CoursePublishVO;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVO getCoursePublishVoById(@Param("courseId") String courseId);

    CourseWebVo selectCourseDetailInfo(String courseId);
}
