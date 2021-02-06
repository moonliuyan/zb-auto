package com.liuyan.zbauto.api.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * 行情信息返回结果
 * 
 * @ClassName: TickerResult
 * @Description: TODO
 * @author: npc QQ:568081714
 * @date:2016年8月29日 下午11:51:42
 */
@Data
public class TickerResult {
	private long date;
	private Ticker ticker;
	

	@Data
	public static class Ticker {
		private double high;
		private double low;
		private double last;
		private double vol;
		private double buy;
		private double sell;
		
		private double volume;
		
		@Override
		public String toString() {
			return "ticker[买:" + buy + " 卖:" + sell + " 高:" + high + " 低:"
					+ low + " last:" + last + ", vol:" + vol + "]";
		}
	}


	@Override
	public String toString() {
		return ticker+"时:" + new DateTime(date);
	}
}