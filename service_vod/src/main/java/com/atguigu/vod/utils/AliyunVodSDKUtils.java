package com.atguigu.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunVodSDKUtils {

    public static DefaultAcsClient initVodClient( String accessId, String accessSecret){
        String regionId = "cn-shanghai"; // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessSecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
