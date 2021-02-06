package com.liuyan.zbauto.api.entity;

import lombok.Data;

@Data
public class Markets {
	private Market btc_usdt;
	private Market bcc_usdt;
	private Market ubtc_usdt;
	private Market ltc_usdt;
	private Market eth_usdt;
	private Market etc_usdt;
	private Market bts_usdt;
	private Market eos_usdt;
	private Market qtum_usdt;
	private Market hsr_usdt;
	private Market xrp_usdt;
	private Market bcd_usdt;
	private Market dash_usdt;
	private Market btc_qc;
	private Market bcc_qc;
	private Market ubtc_qc;
	private Market ltc_qc;
	private Market eth_qc;
	private Market etc_qc;
	private Market bts_qc;
	private Market eos_qc;
	private Market qtum_qc;
	private Market hsr_qc;
	private Market xrp_qc;
	private Market bcd_qc;
	private Market dash_qc;
	private Market bcc_btc;
	private Market ubtc_btc;
	private Market ltc_btc;
	private Market eth_btc;
	private Market etc_btc;
	private Market bts_btc;
	private Market eos_btc;
	private Market qtum_btc;
	private Market hsr_btc;
	private Market xrp_btc;
	private Market bcd_btc;
	private Market dash_btc;
	private Market sbtc_usdt;
	private Market sbtc_qc;
	private Market sbtc_btc;
	private Market ink_usdt;
	private Market ink_qc;
	private Market ink_btc;
	private Market tv_usdt;
	private Market tv_qc;
	private Market tv_btc;
	private Market bcx_usdt;
	private Market bcx_qc;
	private Market bcx_btc;
	private Market bth_usdt;
	private Market bth_qc;
	private Market bth_btc;
	private Market lbtc_usdt;
	private Market lbtc_qc;
	private Market lbtc_btc;
	private Market chat_usdt;
	private Market chat_qc;
	private Market chat_btc;
	private Market hlc_usdt;
	private Market hlc_qc;
	private Market hlc_btc;

	@Data
	public static class Market {
		private int amountScale;
		private int priceScale;
	}
}