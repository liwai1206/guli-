package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> findAllChapterVideo(String courseId);

    boolean removeChapterById(String chapterId);

    void removeChapterByCourseId(String courseId);
}
