package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-07-05
 */
@RestController
@RequestMapping("/eduucenter/ucentermember")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R loginUcenter(@RequestBody UcenterMember member){
        String token = ucenterMemberService.login( member );
        return R.ok().data("token", token);
    }

    @PostMapping("register")
    public R registerUcenter(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register( registerVo );
        return R.ok();
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            com.atguigu.ucenterservice.entity.UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
            return R.ok().data("memberInfo", ucenterMember );
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }

    @GetMapping("getMemberInfoById/{memberId}")
    public UcenterMemberOrder getMemberInfoById(@PathVariable String memberId){
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);

        UcenterMemberOrder ucenterMember1 = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember, ucenterMember1);

        return ucenterMember1;
    }

    // 查询某一天注册的人数
    @GetMapping("registerCountByDay/{day}")
    public R registerCountByDay(@PathVariable String day){
        Integer count = ucenterMemberService.registerCount( day );
        return R.ok().data("registerCount", count);
    }


}

