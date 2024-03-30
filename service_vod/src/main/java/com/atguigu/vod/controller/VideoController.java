package com.atguigu.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.vod.utils.AliyunVodSDKUtils;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VideoController {

    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtils.ACCESSKEYID,
                    ConstantPropertiesUtils.ACCESSKEYSECRET);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();

            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001, "获取播放凭证失败！");
        }
    }

}
