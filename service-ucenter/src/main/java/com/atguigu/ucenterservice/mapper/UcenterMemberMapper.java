package com.atguigu.ucenterservice.mapper;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author 22wli
 * @since 2023-07-05
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer registerCountDay(@Param("day") String day);
}
