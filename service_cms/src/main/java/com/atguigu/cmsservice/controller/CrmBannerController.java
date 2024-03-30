package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 后台管理接口
 * </p>
 *
 * @author 22wli
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/educms/crmbanner")
//@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;


    // 分页查询
    @GetMapping("getPage/{page}/{limit}")
    public R getPage(@PathVariable Integer page,
                     @PathVariable Integer limit){

        Page<CrmBanner> crmBannerPage = new Page<>( page, limit);
        crmBannerService.page(crmBannerPage, null);

        return R.ok().data("items", crmBannerPage.getRecords()).data("total", crmBannerPage.getTotal());

    }

    // 根据id查询
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("item", crmBanner);
    }

    // 保存
    @PostMapping("save")
    public R save( @RequestBody CrmBanner crmBanner ){
        crmBannerService.save( crmBanner );
        return R.ok();
    }

    // 更新
    @PostMapping("update")
    public R update( @RequestBody CrmBanner crmBanner ){
        crmBannerService.updateById( crmBanner );
        return R.ok();
    }

    // 根据id删除
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id ){
        crmBannerService.removeById( id );

        return R.ok();
    }

}

