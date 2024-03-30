package com.atguigu.msmservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RandomUtils;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String > redisTemplate;

    @RequestMapping("send/{phone}")
    public R send(@PathVariable String phone){
        // 首先判断redis中是否已经有code
        String code = (String) redisTemplate.opsForValue().get(phone);
        if ( !StringUtils.isEmpty( code )) return R.ok();

        // 生成四位的验证码
        code = RandomUtils.getFourBitRandom();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);

        // 调用service的方法发送
        boolean isSuccess = msmService.sendMessage(phone, params);

        // 返回
        if ( isSuccess ){
            // 存入redis
            redisTemplate.opsForValue().set( phone, code, 5, TimeUnit.MINUTES);

            return R.ok();
        }else {
            return R.error().message("短信发送失败!");
        }

    }

}
