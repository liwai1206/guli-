package com.atguigu.vod.controller;


import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/eduvod/filevod")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo( file );
        return R.ok().message("视频上传成功").data("videoId", videoId );
    }

    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){

        vodService.deleteVideo( id );

        return R.ok().message("视频删除成功");
    }

    @DeleteMapping("deleteBatchVideo")
    public R deleteBatchVideo(@RequestBody List<String> ids){

        vodService.deleteBatchVideo( ids );

        return R.ok().message("视频删除成功");
    }

}
