package com.liuyan.zbauto.api.entity;


import com.liuyan.zbauto.api.enumtype.SymbolEnum;
import com.liuyan.zbauto.api.enumtype.TradeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author npc
 * 
 *         下单交易
 * 
 * @param symbol
 *            btc_usd: 比特币 ltc_usd: 莱特币
 * @param type
 *            买卖类型： 限价单（buy/sell） 市价单（buy_market/sell_market）
 * @param price
 *            下单价格 [限价买单(必填)： 大于等于0，小于等于1000000 | 市价买单(必填)： BTC :最少买入0.01个BTC
 *            的金额(金额>0.01*卖一价) / LTC :最少买入0.1个LTC 的金额(金额>0.1*卖一价)]
 * 
 * @param amount
 *            交易数量 [限价卖单（必填）：BTC 数量大于等于0.01 / LTC 数量大于等于0.1 | 市价卖单（必填）： BTC
 *            :最少卖出数量大于等于0.01 / LTC :最少卖出数量大于等于0.1]
 *
 */
@Data
@AllArgsConstructor
public class TradeInfo {
	/** 交易时间,需要从k周期获取到 */
	private long tradeDate;
	private SymbolEnum symbolType;
	private TradeEnum tradeType;
	private double price;
	private double amount;
	/** 订单是否有效(针对用于已经优化的,手动下单不用理会) */
	private boolean effective;

	/** 记录失败的原因 */
	// private String remark;

	public TradeInfo(SymbolEnum symbolType, TradeEnum tradeType, double price, double amount) {
		super();
		this.symbolType = symbolType;
		this.tradeType = tradeType;
		this.price = price;
		this.amount = amount;

		this.tradeDate = 0;
		this.effective = true;
		// this.remark = "";
	}
	
	@Override
	public String toString() {
		return "symbolType:" + symbolType + " tradeType:" + tradeType + " price:" + price + " amount:" + amount + " effective:" + effective;
	}

}
