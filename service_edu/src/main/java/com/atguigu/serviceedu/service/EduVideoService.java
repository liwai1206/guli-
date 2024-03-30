package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
public interface EduVideoService extends IService<EduVideo> {

    int getCountByChapterId(String chapterId);

    void removeVideoByCourseId(String courseId);
}
