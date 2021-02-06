package com.liuyan.zbauto.api.enumtype;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public enum TradeEnum implements Serializable{
	buy(1,"buy"), sell(0,"sell"), buy_market(1,"buy_market"), sell_market(
			0,"sell_market"),
	
	cancel(100,"cancel");

	@Getter
	@Setter
	private int type;//1/0[buy/sell]
	@Getter
	@Setter
	private String name;

	private TradeEnum(int type,String name) {
		this.type = type;
		this.name = name;
	}
	
	/** 通过value获取对应的枚举对象 */
	public static TradeEnum getEnum(String name) {
		for (TradeEnum examType : TradeEnum.values()) {
			if (examType.getName().equals(name)) {
				return examType;
			}
		}
		return null;
	}
}
