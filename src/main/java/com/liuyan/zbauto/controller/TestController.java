package com.liuyan.zbauto.controller;

import com.liuyan.zbauto.mapper.entity.TradeRecord;
import com.liuyan.zbauto.service.TradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    private TradeRecordService tradeRecordService;
    @RequestMapping("/test")
    @ResponseBody
    public Object test(){
        TradeRecord tradeRecordbyId = tradeRecordService.getTradeRecordbyId(1);
        return tradeRecordbyId;
    }
}
