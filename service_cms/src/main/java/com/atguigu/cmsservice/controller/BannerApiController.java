package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前台管理接口
 * </p>
 *
 * @author 22wli
 * @since 2023-07-01
 */

@RestController
//@CrossOrigin
@RequestMapping("/educms/crmbannerfront")
public class BannerApiController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("getAll")
    public R getAll(){

        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok().data("items", list);
    }

}
