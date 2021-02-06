package com.liuyan.zbauto.api.entity;

import lombok.Data;

@Data
public class TradeResult {
	private long code;
	private String message;
	private long id;//订单id
}