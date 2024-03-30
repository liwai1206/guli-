package com.atguigu.serviceedu.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除阿里云视频失败");
    }

    @Override
    public R deleteBatchVideo(List<String> ids) {
        return R.error().message("批量删除阿里云视频失败");

    }
}
