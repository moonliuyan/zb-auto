package com.liuyan.zbauto.api.entity;

import lombok.Data;

/**
 * 订单查询
 *@author lianlianyi@vip.qq.com
* @date 2017年12月29日 上午11:06:14
 */
@Data
public class Order {
	private long id;
	private double price;
	/** 挂单总数量 */
	private double total_amount;
	/** 委托时间 */
	private long trade_date;
	/** 挂单状态(1：取消,2：交易完成,3：待成交/待成交未交易部份) */
	private int status;
	/** 已成交总金额 */
	private double trade_money;
	/** 已成交数量 */
	private double trade_amount;
	/** 挂单类型 1/0[buy/sell] */
	private int type;
	/** 交易类型 */
	private String currency;
}