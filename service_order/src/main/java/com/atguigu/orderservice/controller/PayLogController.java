package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/orderservice/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 生成支付二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative( orderNo);
        return R.ok().data(map);
    }


    // 查询订单状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String, String> map = payLogService.queryPayStatus( orderNo );
        if ( map == null ){
            return R.error().message("支付出错");
        }

        if ( map.get("trade_state").equals("SUCCESS")){
            //更改订单状态
            payLogService.updateOrderStatus(map);
            System.out.println("支付成功：" + map);
            return R.ok().message("支付成功");
        }

        System.out.println("正在支付：" + map);
        return R.ok().code(25000).message("支付中");
    }

}

