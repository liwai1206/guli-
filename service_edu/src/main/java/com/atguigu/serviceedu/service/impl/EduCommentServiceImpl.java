package com.atguigu.serviceedu.service.impl;

import com.atguigu.serviceedu.entity.EduComment;
import com.atguigu.serviceedu.mapper.EduCommentMapper;
import com.atguigu.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> findCommentPage(Page<EduComment> pageParam, String courseId) {

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("course_id", courseId);
        baseMapper.selectPage( pageParam,wrapper );

        List<EduComment> records = pageParam.getRecords();
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
