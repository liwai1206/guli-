package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import com.atguigu.serviceedu.client.UcenterClient;
import com.atguigu.serviceedu.entity.EduComment;
import com.atguigu.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/eduservice/educomment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    @PostMapping("insert")
    public R insert(@RequestBody EduComment eduComment, HttpServletRequest request){

        // 首先通过request获取当前登录的用户的id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 如果memberId为空，表示还没有登录
        if (StringUtils.isEmpty( memberId )){
            return R.error().code(20001).message("请登录");
        }

        // 否则，设置member相关的内容到eduComment中
        eduComment.setMemberId( memberId );

        // 调用client接口，根据memberId获得登录的用户的完整信息
        UcenterMemberOrder ucenterMember = ucenterClient.getMemberInfoById(memberId);
        eduComment.setAvatar( ucenterMember.getAvatar() );
        eduComment.setNickname( ucenterMember.getNickname());

        commentService.save( eduComment );
        return R.ok();
    }

    @GetMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable long page,
                      @PathVariable long limit,
                      String courseId){
        Page<EduComment> pageParam = new Page<>(page, limit);

        Map<String,Object> map = commentService.findCommentPage(pageParam, courseId);
        return R.ok().data(map);
    }

}

