package com.atguigu.orderservice.service.impl;

import com.atguigu.commonutils.vo.CourseWebVoOrder;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.mapper.OrderMapper;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public String saveOrder(CourseWebVoOrder courseInfo, UcenterMemberOrder memberInfo) {

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseInfo.getId());
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberInfo.getId());
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert( order );

        return order.getOrderNo();
    }
}
