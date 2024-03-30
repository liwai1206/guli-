package com.atguigu.eduservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelWriterTest {
    public static void main(String[] args) {

        String filename = "F:\\test1.xlsx";

        EasyExcel.write(filename, ExcelEntity.class).sheet("学生信息").doWrite(data());

    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<ExcelEntity> data() {
        List<ExcelEntity> list = new ArrayList<ExcelEntity>();
        for (int i = 0; i < 10; i++) {
            ExcelEntity data = new ExcelEntity();
            data.setSno(i+"");
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
