package com.atguigu.statisticsservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statisticsservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-07-20
 */
@RestController
@RequestMapping("/statisticsservice/statisticsdaily")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("registerCount/{day}")
    public R createStatisticsByDay(@PathVariable String day ){
        statisticsDailyService.createStatisticsByDay( day );
        return R.ok();
    }

    @GetMapping("showChart/{type}/{begin}/{end}")
    public R showChart(@PathVariable String type,
                       @PathVariable String begin,
                       @PathVariable String end){

        Map<String, Object> map = statisticsDailyService.getChartData( type, begin, end );

        return R.ok().data( map );
    }
}

