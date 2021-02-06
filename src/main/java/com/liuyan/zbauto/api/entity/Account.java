package com.liuyan.zbauto.api.entity;


import com.liuyan.zbauto.api.enumtype.SymbolEnum;
import lombok.Data;

import java.util.List;

@Data
public class Account {
	private Result result;

	@Data
	public static class Result {
		private List<Coin> coins;
		private Base base;

		@Data
		public static class Coin {
//			private String cnName;//币种,跟key一样
//			private String enName;//币种,跟key一样
			private double available;
			private double freez;
//			private String unitTag;//币种,跟key一样
			/**小数位*/
			private int unitDecimal;
			private String key;

			@Override
			public String toString() {
				return "Coin{" +
						"key='" + key + '\'' +
						",available=" + available +
						", freez=" + freez +
						", unitDecimal=" + unitDecimal +
						'}';
			}
		}
		/**获取对应的coin,针对交易对[0]*/
		public Coin getCoin(SymbolEnum symbol){
			String coinName = symbol.name().split("_")[0];
			return this.getCoin(coinName);
		}
		/**获取对应的coin,参数:ltc qc usdt...*/
		public Coin getCoin(String coinName){
			Coin coin = null;
			for (Coin c : coins) {
				if(c.getKey().equals(coinName)){
					coin = c;
				}	
			}
			return coin;
		}



		@Data
		public static class Base {
			private boolean auth_google_enabled;
			private boolean auth_mobile_enabled;
			private boolean trade_password_enabled;
			private String username;
		}
	}



}