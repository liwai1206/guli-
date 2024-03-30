package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.client.VodClient;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
@RestController
@RequestMapping("/eduservice/eduvideo")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("save")
    public R save(@RequestBody EduVideo eduVideo){
        eduVideoService.save( eduVideo );
        return R.ok();
    }

    @DeleteMapping("delete/{videoId}")
    public R delete( @PathVariable String videoId){

        // 首先获取video_source_id
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();

        if ( !StringUtils.isEmpty( videoSourceId )){
            // 根据videoSourceId删除阿里云的视频
            R r = vodClient.deleteVideo(videoSourceId);
            if ( r.getCode() == 20001){
                return r;
            }
        }

        eduVideoService.removeById( videoId );
        return R.ok();


    }

    @PostMapping("update")
    public R update(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById( eduVideo );
        return R.ok();
    }

    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo", eduVideo);
    }

}

