package com.atguigu.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.excel.ExcelSubjectData;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() { }

    //创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    // 一行一行读取
    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {

        if ( subjectData== null ){
            throw new GuliException( 20001, "添加失败");
        }

        // 添加一级分类
        // 1.首先判断一级分类是否存在
        EduSubject existOneSubject = this.existOneSubject( eduSubjectService, subjectData.getOneSubjectName() );

        // 2.如果一级分类不存在，则添加到数据库
        if ( existOneSubject == null ){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0");

            eduSubjectService.save( existOneSubject );
        }

        // 添加二级分类
        // 获取父节点id
        String pid = existOneSubject.getId();
        // 1.首先判断二级分类是否存在
        EduSubject existTwoSubject = this.existTwoSubject( eduSubjectService, subjectData.getTwoSubjectName(), pid );

        // 2.如果二级分类不存在，则添加到数据库
        if ( existTwoSubject == null ){
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId( pid );

            eduSubjectService.save( existTwoSubject );
        }
    }

    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String twoSubjectName, String pid) {

        // 在数据库查询，返回查询到的结果
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", twoSubjectName)
                .eq("parent_id", pid);

        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);

        return eduSubject;
    }

    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String oneSubjectName) {

        // 在数据库查询，返回查询到的结果
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", oneSubjectName)
                .eq("parent_id", "0");

        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);

        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
