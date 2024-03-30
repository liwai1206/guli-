package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> findCommentPage(Page<EduComment> pageParam, String courseId);
}
