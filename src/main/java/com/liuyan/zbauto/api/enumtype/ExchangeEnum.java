package com.liuyan.zbauto.api.enumtype;

import java.io.Serializable;

public enum ExchangeEnum implements Serializable{
	zb,
	okex,
	bitfinex,
	gate,
	huobiPro,
	poloniex,
	exx,
	aex,
	zbg
	;
	
	
	/** 通过value获取对应的枚举对象*/
	public static ExchangeEnum getEnum(String value) {
		for (ExchangeEnum examType : ExchangeEnum.values()) {
			if (examType.name().equals(value)) {
				return examType;
			}
		}
		return null;
	}
}
