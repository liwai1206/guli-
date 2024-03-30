package com.atguigu.serviceedu.service.impl;

import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.mapper.EduTeacherMapper;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-14
 */
@Transactional(readOnly = true)
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Cacheable(value = "teacher", key = "'eduTeacherList'")
    @Override
    public List<EduTeacher> selectFrontTeacher() {
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(teacherWrapper);
        return eduTeachers;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam) {

        // 分页查询数据，然后将所有数据逐个添加到map集合中
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage( pageParam, wrapper);

        List<EduTeacher> records = pageParam.getRecords();
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
}
