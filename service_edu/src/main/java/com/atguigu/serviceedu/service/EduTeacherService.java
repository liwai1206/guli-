package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> selectFrontTeacher();

    Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
