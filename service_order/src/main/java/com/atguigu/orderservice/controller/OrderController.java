package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.CourseWebVoOrder;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import com.atguigu.orderservice.client.EduClient;
import com.atguigu.orderservice.client.UcenterClient;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/orderservice/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    // 根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){

        // 根据courseId查询课程信息
        CourseWebVoOrder courseInfo = eduClient.getCourseInfoById(courseId);

        // 根据request获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id获取用户信息
        UcenterMemberOrder memberInfo = ucenterClient.getMemberInfoById(memberId);

        String orderNo = orderService.saveOrder(courseInfo, memberInfo);

        return R.ok().data("orderNo", orderNo);
    }

    // 根据orderNo获取订单信息
    @GetMapping("getOrderByOrderNo/{orderNo}")
    public R getOrderByOrderNo( @PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }

    // 根据courseId和memberId查询课程是否已经购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);

        if ( count > 0 ){
            return true;
        }else {
            return false;
        }
    }

}

