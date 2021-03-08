package com.liuyan.zbauto.wechat;

public interface WechatService {

    public String getWeChatAccessTokenUrl();

    /**
     * 发送微信通知
     * @param title
     * @param message
     */
    public void notice(String title,String message);
}

