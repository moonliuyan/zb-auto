package com.liuyan.zbauto.api.enumtype;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public enum KTimeEnum implements Serializable{
	min1(1,"1min","1分钟"),
	min3(3,"3min","3分钟"),
	min5(5,"5min","5分钟"),
	min15(15,"15min","15分钟"),
	min30(30,"30min","30分钟"),
	day1(24*60,"1day","1天"),
	day3(24*60 * 3,"1day","3天"),
	week1(24*60 * 7,"1week","1周"),
	hour1(60,"1hour","1小时"),
	hour2(60 * 2,"2hour","2小时"),
	hour4(60 * 4,"4hour","4小时"),
	hour6(60 * 6,"6hour","6小时"),
	hour12(60 * 12,"12hour","12小时")
	;
	
	@Getter
	@Setter
	private int key;
	@Getter
	@Setter
	private String time;
	@Getter
	@Setter
	private String name;
	
	/**必须私有构造函数**/
	private KTimeEnum(int key,String kt,String name){
		this.key = key;
		this.time = kt;
		this.name = name;
	}
	
	/** 通过value获取对应的枚举对象*/
	public static KTimeEnum getEnum(int value) {
		for (KTimeEnum examType : KTimeEnum.values()) {
			if (examType.getKey() == value) {
				return examType;
			}
		}
		return null;
	}
	
	/** 通过value获取对应的枚举对象*/
	public static KTimeEnum getEnum(String value) {
		for (KTimeEnum examType : KTimeEnum.values()) {
			if (examType.getTime().equals(value)) {
				return examType;
			}
		}
		return null;
	}
}
