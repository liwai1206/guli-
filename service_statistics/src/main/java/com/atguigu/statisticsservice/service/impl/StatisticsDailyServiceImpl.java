package com.atguigu.statisticsservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statisticsservice.client.UcenterClient;
import com.atguigu.statisticsservice.entity.StatisticsDaily;
import com.atguigu.statisticsservice.mapper.StatisticsDailyMapper;
import com.atguigu.statisticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-07-20
 */
@Service
@Transactional(readOnly = true)
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void createStatisticsByDay(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete( wrapper );

        R registerCountByDay = ucenterClient.registerCountByDay(day);
        Integer registerCount = (Integer) registerCountByDay.getData().get("registerCount");
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated( day );
        statisticsDaily.setRegisterNum( registerCount );

        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        statisticsDaily.setLoginNum( loginNum );
        statisticsDaily.setVideoViewNum( videoViewNum );
        statisticsDaily.setCourseNum( courseNum );

        baseMapper.insert( statisticsDaily );
    }

    @Override
    public Map<String, Object> getChartData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select(type, "date_calculated");
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        List<String> dateList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();

        for (StatisticsDaily daily : dailyList) {
            dateList.add( daily.getDateCalculated() );

            switch ( type ){
                case "register_num":
                    dataList.add( daily.getRegisterNum() );
                    break;
                case "login_num":
                    dataList.add( daily.getLoginNum() );
                    break;
                case "video_view_num":
                    dataList.add( daily.getVideoViewNum() );
                    break;
                case "course_num":
                    dataList.add( daily.getCourseNum() );
                    break;
                default:
                    break;
            }

        }

        Map<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("dataList", dataList);

        return map;
    }
}
