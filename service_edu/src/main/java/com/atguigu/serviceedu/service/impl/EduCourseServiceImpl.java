package com.atguigu.serviceedu.service.impl;

import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduCourseDescription;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.CourseInfoVO;
import com.atguigu.serviceedu.entity.vo.CoursePublishVO;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.atguigu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseDescriptionService;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
@Service
@Transactional(readOnly = true)
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {

        // 1.保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if ( insert <= 0){
            throw new GuliException(20001, "课程信息保存失败");
        }

        // 获取新增的课程信息的id
        String courseId = eduCourse.getId();

        // 2.保存课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription( courseInfoVO.getDescription() );
        eduCourseDescription.setId( courseId );
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if ( !save ){
            throw new GuliException(20001, "课程详情信息保存失败");
        }

        return courseId;
    }

    @Override
    public CourseInfoVO getCourseInfoById(String courseId) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();

        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties( eduCourse, courseInfoVO);

        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVO.setDescription( courseDescription.getDescription());

        return courseInfoVO;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties( courseInfoVO, eduCourse );
        int update = baseMapper.updateById(eduCourse);
        if ( update <= 0){
            throw new GuliException(20001, "更新课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId( eduCourse.getId() );
        eduCourseDescription.setDescription( courseInfoVO.getDescription() );
        boolean updateById = eduCourseDescriptionService.updateById(eduCourseDescription);
        if ( !updateById ){
            throw new GuliException(20001, "更新课程详情信息失败");
        }


    }

    @Override
    public CoursePublishVO getCoursePublishVoById(String courseId) {

        return baseMapper.getCoursePublishVoById( courseId );
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void deleteCourseById(String courseId) {
        // 根据courseId删除video
        eduVideoService.removeVideoByCourseId( courseId );


        //根据courseId删除chapter
        eduChapterService.removeChapterByCourseId( courseId );

        // 根据courseId删除description
        eduCourseDescriptionService.removeById( courseId );

        //删除当前course记录
        int delete = baseMapper.deleteById(courseId);

        if ( delete <=0 ){
            throw new GuliException(20001, "课程删除失败");
        }
    }


    @Cacheable(value = "course", key = "'eduCourseList'")
    @Override
    public List<EduCourse> selectFrontCourse() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(courseWrapper);
        return eduCourses;
    }

    @Override
    public Map<String, Object> pageListFront(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if ( !StringUtils.isEmpty( courseQueryVo.getSubjectId() )){
            queryWrapper.eq("subject_id", courseQueryVo.getSubjectId());
        }
        if ( !StringUtils.isEmpty( courseQueryVo.getSubjectParentId() )){
            queryWrapper.eq("subject_parent_id", courseQueryVo.getSubjectParentId());
        }
        if ( !StringUtils.isEmpty( courseQueryVo.getBuyCountSort() )){
            queryWrapper.orderByDesc("buy_count");
        }
        if ( !StringUtils.isEmpty( courseQueryVo.getGmtCreateSort() )){
            queryWrapper.orderByDesc("gmt_create");
        }
        if ( !StringUtils.isEmpty( courseQueryVo.getPriceSort() )){
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage( pageParam, queryWrapper );

        List<EduCourse> records = pageParam.getRecords();
        long pages = pageParam.getPages();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        HashMap<String, Object> map = new HashMap<>();
        map.put("pages", pages);
        map.put("current", current);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        map.put("records", records);

        return map;
    }

    @Override
    public CourseWebVo findCourseDetailInfo(String courseId) {

        return baseMapper.selectCourseDetailInfo( courseId );
    }

}
