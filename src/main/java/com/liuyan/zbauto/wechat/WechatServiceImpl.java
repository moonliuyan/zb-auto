package com.liuyan.zbauto.wechat;


import com.alibaba.fastjson.JSONObject;
import com.liuyan.zbauto.mapper.WxAccessTokenMapper;
import com.liuyan.zbauto.mapper.entity.WxAccessToken;
import com.liuyan.zbauto.util.HttpUtilManager;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class WechatServiceImpl implements WechatService {
    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.secretId}")
    private String secret;
    @Value("${wechat.accessUrl}")
    private String accessUrl;
    @Autowired
    WxAccessTokenMapper wxAccessTokenMapper;


    public String getWeChatAccessTokenUrl() {
        String json="";
        WxAccessToken wxAccessToken = wxAccessTokenMapper.selectByPrimaryKey(1);
        try {

            if(wxAccessToken!=null){
                Date updateTime = wxAccessToken.getUpdateTime();
                if(new Date().getTime()-updateTime.getTime()>2*60*60*1000){
                    String accessToken = getAccessToken();
                    wxAccessToken.setAccessToken(accessToken);
                    wxAccessToken.setUpdateTime(new Date());
                    wxAccessTokenMapper.updateByPrimaryKey(wxAccessToken);
                }
            }else{
                String accessToken = getAccessToken();
                wxAccessToken=new WxAccessToken();
                wxAccessToken.setAccessToken(accessToken.toString());
                wxAccessToken.setCreateTime(new Date());
                wxAccessToken.setUpdateTime(new Date());
                wxAccessTokenMapper.insert(wxAccessToken);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return "请求获取access_token出现异常";
        }
        return wxAccessToken.getAccessToken();
    }

    private String getAccessToken(){
        String url = accessUrl.replace("APPID",appId).replace("APPSECRET",secret);
        String json = null;
        try {
            json = HttpUtilManager.getInstance().requestHttpGet(url, new LinkedHashMap<>());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        Object accessToken = jsonObject.get("access_token");
        return accessToken.toString();
    }
}
