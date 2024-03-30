package com.atguigu.statisticsservice.schedule;

import com.atguigu.statisticsservice.service.StatisticsDailyService;
import com.atguigu.statisticsservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void generateStatisticsData(){
        // 获取前一天的日期
        String date = DateUtils.formatDate(DateUtils.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay( date );
    }
}
