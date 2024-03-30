package com.atguigu.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.excel.ExcelSubjectData;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.entity.subject.SecondSubject;
import com.atguigu.serviceedu.listener.SubjectExcelListener;
import com.atguigu.serviceedu.mapper.EduSubjectMapper;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-06-21
 */
@Transactional(readOnly = true)
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            InputStream inputStream = file.getInputStream();

            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw  new GuliException(20002, "添加课程分类失败");
        }
    }

    @Override
    public List<OneSubject> findAllOneSubject() {
        // 获取所有的一级分类
        QueryWrapper<EduSubject> oneSubjectQueryWrapper = new QueryWrapper<>();
        oneSubjectQueryWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneSubjectQueryWrapper);

        // 获取所有的二级分类
        QueryWrapper<EduSubject> twoSubjectQueryWrapper = new QueryWrapper<>();
        oneSubjectQueryWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoSubjectQueryWrapper);

        // 创建一个集合，用于封装最终的结果集
        List<OneSubject> list = new ArrayList<>();

        // 遍历所有一级分类
        for (EduSubject eduSubject : oneSubjectList) {
            // 封装一级分类
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties( eduSubject, oneSubject );

            list.add( oneSubject );

            // 封装二级分类
            List<SecondSubject> secondSubjectList = new ArrayList<>();
            for (EduSubject subject : twoSubjectList) {
                if ( subject.getParentId().equals( eduSubject.getId())){
                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties( subject, secondSubject );
                    secondSubjectList.add( secondSubject );
                }
            }

            oneSubject.setChildren( secondSubjectList );
        }

        return list;
    }
}
