package com.atguigu.serviceedu.entity.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
