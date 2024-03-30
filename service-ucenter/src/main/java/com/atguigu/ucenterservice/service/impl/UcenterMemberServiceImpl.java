package com.atguigu.ucenterservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 22wli
 * @since 2023-07-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {

        // 获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 判空
        if (StringUtils.isEmpty( mobile ) || StringUtils.isEmpty( password )){
            throw new GuliException(20001, "登录失败");
        }

        // 判断用户是否存在
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if ( ucenterMember == null ){
            throw new GuliException(20001, "登录失败");
        }

        // 判断密码是否正确
        if ( !MD5.encrypt( password ).equals( ucenterMember.getPassword())){
            throw new GuliException(20001, "登录失败");
        }

        // 判断该用户是否被禁用了
        if ( ucenterMember.getIsDisabled() ){
            throw new GuliException(20001, "登录失败");
        }

        // 否则，登录成功
        // 生成token信息
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        // 获取各个属性的值
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        // 判断是否为空
        if ( StringUtils.isEmpty( mobile ) ||
                StringUtils.isEmpty( nickname ) ||
                StringUtils.isEmpty( password ) ||
                StringUtils.isEmpty( code ) ){
            throw new GuliException(20001, "注册失败");
        }

        // 判断code是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if ( !code.equals( redisCode )){
            throw new GuliException(20001, "注册失败,验证码错误!");
        }

        // 判断当前手机号是否已经存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if ( count > 0 ){
            throw new GuliException(20001, "注册失败,此手机号已被使用!");
        }

        // 注册成功，添加数据
        UcenterMember ucenterMember1 = new UcenterMember();
        ucenterMember1.setMobile( mobile );
        ucenterMember1.setNickname( nickname );
        ucenterMember1.setPassword( MD5.encrypt( password ));
        ucenterMember1.setIsDisabled( false );
        ucenterMember1.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS\n" +
                "4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert( ucenterMember1 );

    }

    @Override
    public UcenterMember getByOpenId(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }

    @Override
    public Integer registerCount(String day) {

        Integer count = baseMapper.registerCountDay(day);
        return count;
    }
}
