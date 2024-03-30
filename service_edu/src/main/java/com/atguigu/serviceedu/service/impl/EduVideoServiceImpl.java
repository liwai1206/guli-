package com.atguigu.serviceedu.service.impl;

import com.atguigu.serviceedu.client.VodClient;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.mapper.EduVideoMapper;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
@Transactional(readOnly = true)
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public int getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = baseMapper.selectCount( wrapper );
        return count;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");

        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);

        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if ( !StringUtils.isEmpty(eduVideo.getVideoSourceId()))
                ids.add( eduVideo.getVideoSourceId() );
        }

        if ( ids.size() >0 ){
            // 删除阿里云视频
            vodClient.deleteBatchVideo( ids );
        }

        // 删除数据库数据
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        baseMapper.delete( queryWrapper2 );
    }
}
