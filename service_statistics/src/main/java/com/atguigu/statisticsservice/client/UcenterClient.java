package com.atguigu.statisticsservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {
    // 查询某一天注册的人数
    @GetMapping("/eduucenter/ucentermember/registerCountByDay/{day}")
    public R registerCountByDay(@PathVariable("day") String day);
}
