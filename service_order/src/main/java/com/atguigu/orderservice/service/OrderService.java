package com.atguigu.orderservice.service;

import com.atguigu.commonutils.vo.CourseWebVoOrder;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import com.atguigu.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
public interface OrderService extends IService<Order> {

    String saveOrder(CourseWebVoOrder courseInfo, UcenterMemberOrder memberInfo);
}
