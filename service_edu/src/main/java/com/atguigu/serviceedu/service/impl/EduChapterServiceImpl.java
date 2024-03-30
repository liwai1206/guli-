package com.atguigu.serviceedu.service.impl;

import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.chapter.ChapterVO;
import com.atguigu.serviceedu.entity.chapter.VideoVO;
import com.atguigu.serviceedu.mapper.EduChapterMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVO> findAllChapterVideo(String courseId) {

        // 1.根据课程id查询所有的章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        // 2.根据课程id查询所有的小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = eduVideoService.list(videoQueryWrapper);

        // 新建一个list用于返回
        ArrayList<ChapterVO> finalList = new ArrayList<>();
        // 3.遍历所有章节
        for (EduChapter eduChapter : chapterList) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            finalList.add( chapterVO );

            // 新建一个list用于存储当前章节的所有小节
            ArrayList<VideoVO> videoVOS = new ArrayList<>();
            // 遍历所有小节
            for (EduVideo eduVideo : videoList) {
                if ( eduVideo.getChapterId().equals( eduChapter.getId())){
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO );
                    videoVOS.add( videoVO );
                }
            }
            chapterVO.setChildren( videoVOS );
        }

        return finalList;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public boolean removeChapterById(String chapterId) {

        //根据id查询是否存在视频，如果有则提示用户尚有子节点
        int count = eduVideoService.getCountByChapterId(chapterId);
        if ( count > 0 ){
            throw new GuliException(20001, "该分章节下存在视频课程，请先删除视频课程");
        }else {
            int delete = baseMapper.deleteById(chapterId);
            return delete >0 ;
        }

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);

        baseMapper.delete( eduChapterQueryWrapper );
    }
}
