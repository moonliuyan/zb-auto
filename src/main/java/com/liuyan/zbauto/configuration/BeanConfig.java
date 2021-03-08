package com.liuyan.zbauto.configuration;

import com.liuyan.zbauto.api.UserApi;
import com.liuyan.zbauto.api.enumtype.ExchangeEnum;
import jodd.props.Props;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class BeanConfig {
    @Bean
    public UserApi userApi(){
        Props p = new Props();
        try {
            p.load(new File("D:\\秘钥.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String apiKey = p.getValue("user.zb.apikey");// 修改为自己的公钥
        String secretKey = p.getValue("user.zb.secretKey");// 修改为自己的私钥
        return new UserApi(ExchangeEnum.zb, "不赚十万不回头", apiKey, secretKey);
    }
}
