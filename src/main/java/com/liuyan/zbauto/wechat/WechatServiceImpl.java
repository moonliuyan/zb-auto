package com.liuyan.zbauto.wechat;

import com.liuyan.zbauto.util.HttpUtilManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class WechatServiceImpl implements WechatService {
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.secretId}")
    private String secret;
    @Value("${wechat.accessUrl}")
    private String accessUrl;


    public String getWeChatAccessTokenUrl() {
        String json="";
        try {
            String url = accessUrl.replace("APPID",appId).replace("APPSECRET",secret);
            json = HttpUtilManager.getInstance().requestHttpGet(url, new LinkedHashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return "请求获取access_token出现异常";
        }
        return json;
    }
}
