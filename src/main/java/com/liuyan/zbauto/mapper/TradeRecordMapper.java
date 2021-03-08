package com.liuyan.zbauto.mapper;

import com.liuyan.zbauto.mapper.entity.TradeRecord;

public interface TradeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeRecord record);

    int insertSelective(TradeRecord record);

    TradeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeRecord record);

    int updateByPrimaryKey(TradeRecord record);
}