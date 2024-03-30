package com.atguigu.eduservice.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExcelEntity {

    //设置表头名称
    @ExcelProperty(value = "学生编号", index = 0)
    private String sno;

    //设置表头名称
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

}
