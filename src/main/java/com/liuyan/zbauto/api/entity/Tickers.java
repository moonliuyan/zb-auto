package com.liuyan.zbauto.api.entity;

import lombok.Data;


@Data
public class Tickers {
	private Ticker ticker;
	private String date;

	@Data
	public static class Ticker {
		private Double high;
		private Double low;
		private Double last;
		private Double vol;
		private Double buy;
		private Double sell;
	}
}