package com.liuyan.zbauto.service.impl;

import com.liuyan.zbauto.mapper.TradeRecordMapper;
import com.liuyan.zbauto.mapper.entity.TradeRecord;
import com.liuyan.zbauto.service.TradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeRecordServiceImpl implements TradeRecordService {
    @Autowired
    TradeRecordMapper tradeRecordMapper;

    public TradeRecord getTradeRecordbyId(long id){
        return tradeRecordMapper.selectByPrimaryKey(id);
    }
}
