package com.liuyan.zbauto.mapper;

import com.liuyan.zbauto.mapper.entity.WxAccessToken;

public interface WxAccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxAccessToken record);

    int insertSelective(WxAccessToken record);

    WxAccessToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxAccessToken record);

    int updateByPrimaryKey(WxAccessToken record);
}