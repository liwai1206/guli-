package com.atguigu.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<ExcelEntity> {

    //一行一行去读取excle内容
    @Override
    public void invoke(ExcelEntity excelEntity, AnalysisContext analysisContext) {
        System.out.println( "********" + excelEntity );
    }

    // 读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println( "表头" + headMap );

    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
