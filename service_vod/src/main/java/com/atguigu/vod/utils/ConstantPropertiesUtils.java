package com.atguigu.vod.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String accessKeyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String accessKeySecret;

    public static String ACCESSKEYID;
    public static String ACCESSKEYSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESSKEYID = accessKeyId;
        ACCESSKEYSECRET = accessKeySecret;
    }
}
