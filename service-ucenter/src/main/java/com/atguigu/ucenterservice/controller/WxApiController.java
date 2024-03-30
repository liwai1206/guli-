package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.servicebase.exception.handler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.atguigu.ucenterservice.util.ConstantPropertiesUtil;
import com.atguigu.ucenterservice.util.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback( String code, String state ){
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        // 发送请求
        String result = "";
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println( "result: " + result  );
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }

        // 解析result
        Gson gson = new Gson();
        HashMap<String, Object> map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        // 查询数据库，当前用户是否曾经使用过微信登陆
        UcenterMember ucenterMember = memberService.getByOpenId( openid );
        if ( ucenterMember == null ){
            // 需要先注册
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            // 解析resultUserInfo
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");

            // 插入数据
            ucenterMember = new UcenterMember();
            ucenterMember.setOpenid( openid );
            ucenterMember.setNickname( nickname );
            ucenterMember.setAvatar( headimgurl );
            System.out.println("ucenterMember==========" + ucenterMember);
            memberService.save( ucenterMember );
        }

        // 如果有注册过，则直接登录
        // 生成令牌,前台能获取令牌，则表示已经登录
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return "redirect:http://localhost:3000?token="+jwtToken;

    }


    // 生成微信登陆的二维码
    @GetMapping("login")
    public String genQrConnect(){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_uri进行编码
        String redirect_uri = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirect_uri = URLEncoder.encode( redirect_uri, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String url = String.format(baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirect_uri,
                "atguigu");

        return "redirect:"+url ;

    }
}
