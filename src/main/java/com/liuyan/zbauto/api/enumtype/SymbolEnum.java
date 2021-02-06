package com.liuyan.zbauto.api.enumtype;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum SymbolEnum implements Serializable {
	btc_usd, ltc_usd,eth_usd,etc_usd,bch_usd;

	/** 通过value获取对应的枚举对象 */
	public static SymbolEnum getEnum(String value) {
		for (SymbolEnum examType : SymbolEnum.values()) {
			if (value.equals(examType.name())) {
				return examType;
			}
		}
		return null;
	}
}
