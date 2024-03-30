package com.atguigu.eduservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelReaderTest {
    public static void main(String[] args) {

        String filename = "F:\\test1.xlsx";

        EasyExcel.read(filename, ExcelEntity.class, new ExcelListener()).sheet().doRead();

    }


}
