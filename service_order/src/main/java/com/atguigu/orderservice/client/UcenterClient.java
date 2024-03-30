package com.atguigu.orderservice.client;


import com.atguigu.commonutils.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/eduucenter/ucentermember/getMemberInfoById/{memberId}")
    public UcenterMemberOrder getMemberInfoById(@PathVariable("memberId") String memberId);
}
