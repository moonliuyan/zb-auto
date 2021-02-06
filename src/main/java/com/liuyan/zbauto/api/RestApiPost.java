
package com.liuyan.zbauto.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PathKit;

import com.liuyan.zbauto.api.entity.Account;
import com.liuyan.zbauto.api.entity.Order;
import com.liuyan.zbauto.api.entity.Tickers;
import com.liuyan.zbauto.api.entity.TradeResult;
import com.liuyan.zbauto.api.enumtype.TradeEnum;
import com.liuyan.zbauto.util.EncryDigestUtil;
import com.liuyan.zbauto.util.MapSort;
import jodd.http.HttpRequest;
import jodd.io.FileUtil;
import jodd.template.MapTemplateParser;
import jodd.util.StringUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

@Data
@Slf4j
public class RestApiPost {
	private String symbol;
	private UserApi userApi;
	private String url="https://trade.zb.center/api/";



	public RestApiPost(String symbol, UserApi userApi) {
		this.userApi = userApi;
		this.symbol = symbol;
	}

	/**
	 * 获取子账号列表接口
	 */
	public void getSubUserList() {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getSubUserList");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		log.debug("参数:" + params);
		String json = this.getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "子账户列表::" + json);
	}

	/**
	 * 添加子账号接口
	 */
	public void addSubUser() {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("method", "addSubUser");
		params.put("password", "pass123457");
		params.put("subUserName", "test33");
		params.put("memo", "hah");

		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "添加子账号接口返回:" + json);
	}

	/** 主子账号内部转账 */
	public void doTransferFunds() {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("amount", "1");
		params.put("method", "doTransferFunds");

		params.put("currency", "doge");
		params.put("fromUserName", "lianlianyi@vip.qq.com");
		params.put("toUserName", "lianlianyi@test12");

		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "主子账号互转返回:" + json);
	}

	/**
	 * 创建子账号APIkey
	 */
	public void createSubUserKey() {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		params.put("method", "createSubUserKey");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("toUserId", "1325607");
		params.put("keyName", "myKey11");
//		params.put("apiIpBind", "");//不要传""
		params.put("assetPerm", "true");// 查询资产权限：查询账号及资产情况:0-不可以，1:可以'
		params.put("entrustPerm", "true");// 委托交易权限：委托,取消,查询交易订单:0-不可以，1:可以
		params.put("moneyPerm", "true");// 充值提币权限：获取充值,提币地址和记录，发起提币业务:0-不可以，1:可以
		params.put("leverPerm", "true");// 杠杆理财权限：理财管理,借贷管理:0-不可以，1:可以

		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "下单返回:" + json);
	}

	/** 现价买入 */
	public TradeResult buy(double price, double amount) {

		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		params.put("method", "order");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("price", Convert.toStr(price));
		params.put("amount", Convert.toStr(amount));
		params.put("tradeType", "1");// 交易类型1/0[buy/sell]
		params.put("currency", symbol);// 交易类型(目前仅支持BTC/LTC/ETH)
		params.put("acctType", "0");
		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "下单返回:" + json);
		return JSONObject.parseObject(json, TradeResult.class);
	}

	/** 现价卖出 */
	public TradeResult sell(double price, double amount) {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		params.put("method", "order");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("price", Convert.toStr(price));
		params.put("amount", Convert.toStr(amount));
		params.put("tradeType", "0");// 交易类型1/0[buy/sell]
		params.put("currency", symbol);// 交易类型(目前仅支持BTC/LTC/ETH)
		params.put("acctType", "0");
		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "下单返回:" + json);
		return JSONObject.parseObject(json, TradeResult.class);
	}

	/** 获取订单信息 **/
	public Order getOrder(long orderId) {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey,3.id,4.currency
		params.put("method", "getOrder");
		params.put("accesskey", userApi.getApiKey());//
		// 这个需要加入签名,放前面
		params.put("id", orderId + "");
		params.put("currency", symbol);// 默认
		String json = this.getJsonPost(params);

		log.debug("执行时间:" + timer.intervalMs() + "查询订单:" + json);
		return JSONObject.parseObject(json, Order.class);

	}

	/**
	 * 获取所有订单,获取多个委托买单或卖单，每次请求返回10条记录(返回null则没有订单)
	 *
	 */
	public void getOrders(TradeEnum tradeType, int pageIndex) {
		TimeInterval timer = DateUtil.timer();
		// 会抛出json{"code":3001,"message":"挂单没有找到"}
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey,3.id,4.currency
		params.put("method", "getOrders");
		params.put("accesskey", userApi.getApiKey());//
		// 这个需要加入签名,放前面
		params.put("tradeType", Convert.toStr(tradeType.getType()));
		params.put("currency", symbol);
		params.put("pageIndex", pageIndex + "");
		// log.debug(params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "获取多个委托:" + json);
	}

	/**
	 * 获取未成交或部份成交的买单和卖单，每次请求返回pageSize<=100条记录
	 *
	 * @param pageIndex
	 * @return
	 */
	public List<Order> getUnfinishedOrdersIgnoreTradeType(int pageIndex) {
		// 会抛出json{"code":3001,"message":"挂单没有找到"}
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey,3.id,4.currency
		params.put("method", "getUnfinishedOrdersIgnoreTradeType");
		params.put("accesskey", userApi.getApiKey());//
		// 这个需要加入签名,放前面
		params.put("currency", symbol);
		params.put("pageIndex", Convert.toStr(pageIndex));
		params.put("pageSize", "10");// 每页数量
		log.debug("参数:" + params);
		String json = getJsonPost(params);
		log.debug("未成交订单:" + json);
		try {
			return JSONObject.parseArray(json, Order.class);
		} catch (Exception e) {

		}
		return new ArrayList<Order>();
	}

	/** 取消订单 **/
	public TradeResult cancelOrder(long orderId) {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey,3.id,4.currency
		params.put("method", "cancelOrder");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("id", orderId + "");
		params.put("currency", symbol);// 默认
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "取消:" + json);
		return JSONObject.parseObject(json, TradeResult.class);
	}

	/** 获取用户信息 **/
	public Account getAccount() {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getAccountInfo");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		log.debug("参数:" + params);
		String json = this.getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "用户信息:" + json);

		return JSONObject.parseObject(json, Account.class);
	}
	
	private void gen(Map<String, String> params) {
		String template = PathKit.getRootClassPath() + "/restJava.template";
		val data = new TreeMap<String, String>();
		String method = params.get("method");
		data.put("method", method);
		data.put("apiKey", params.get("accesskey"));
		data.put("secretKey", userApi.getSecretKey());
		String paramsStr = toStringMap(params);
		data.put("paramsStr", paramsStr);
		String secret = EncryDigestUtil.digest(userApi.getSecretKey());
		data.put("secret", secret);
		String sign = EncryDigestUtil.hmacSign(paramsStr, secret);
		data.put("sign", sign);
		String finalUrl = url + method + "?" + this.buildMap(params);
		finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
		data.put("finalUrl", finalUrl);
		data.put("requestJson", JSONObject.toJSONString(data));

		try {
			String html = new MapTemplateParser().of(data).parse(FileUtil.readString(template));
			System.out.println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void genPython(Map<String, String> params) {
		String template = PathKit.getRootClassPath() + "/restpython.template";
		val data = new TreeMap<String, String>();
		String method = params.get("method");
		data.put("method", method);
		data.put("apiKey", params.get("accesskey"));
		data.put("secretKey", userApi.getSecretKey());
		String paramsStr = toStringMap(params);
		data.put("paramsStr", paramsStr);
		String secret = EncryDigestUtil.digest(userApi.getSecretKey());
		data.put("secret", secret);
		String sign = EncryDigestUtil.hmacSign(paramsStr, secret);
		data.put("sign", sign);
		data.put("url_method", url + method);
		String finalUrl = url + method + "?" + this.buildMap(params);
		finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
		data.put("finalUrl", finalUrl);
		data.put("requestJson", JSONObject.toJSONString(data));

		try {
			String html = new MapTemplateParser().of(data).parse(FileUtil.readString(template));
			System.out.println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getAccountTemp() {
		//测试apiKey:${apiKey}
		//测试secretKey:${secretKey}
		//加密类:https://github.com/zb2017/api/blob/master/zb_netty_client_java/src/main/java/websocketx/client/EncryDigestUtil.java
		//secretKey通过sha1加密得到:${secret}
		String digest = EncryDigestUtil.digest("${secretKey}");
		//参数按照ASCII值排序
		String paramStr = "${paramsStr}";
		//sign通过HmacMD5加密得到:${sign}
		String sign = EncryDigestUtil.hmacSign(paramStr, digest);
		//组装最终发送的请求url
		String finalUrl = "${finalUrl}";
		//取得返回结果json
		String resultJson = HttpRequest.get(finalUrl).send().bodyText();
	}

	/** 获取用户充值地址 */
	public String getUserAddress() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getUserAddress");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("currency", symbol.substring(0, symbol.indexOf("_")));// 默认,需要去掉_cny后缀

		String json = this.getJsonPost(params);
		return json;
	}

	/** 获取用户认证的提现地址 */
	public String getWithdrawAddress() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getWithdrawAddress");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("currency", symbol.substring(0, symbol.indexOf("_")));

		String json = this.getJsonPost(params);
		return json;
	}

	// /**提现*/
	//// 提现矿工费
	//// 比特币最低是0.0003
	//// 莱特币最低是0.001
	//// 以太币最低是0.01
	//// ETC最低是0.01
	//// BTS最低是3
	//// EOS最低是1
	public String withdraw(double amount, double fees, String receiveAddr) {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey

		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("amount", Convert.toStr(amount));// 提现金额
//		params.put("currency", symbol.substring(0, symbol.indexOf("_")));
		params.put("currency", "ada");
		params.put("fees", Convert.toStr(fees));
		params.put("itransfer", "0");// 是否同意bitbank系内部转账(0不同意，1同意，默认不同意)
		params.put("method", "withdraw");
		params.put("receiveAddr", receiveAddr);// 接收地址（必须是认证了的地址，bts的话，以"账户_备注"这样的格式）
		params.put("safePwd", userApi.getPayPass());

		String json = this.getJsonPost(params);
		return json;
	}
	
	public String getLeverAssetsInfo() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getLeverAssetsInfo");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String getLeverBills() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getLeverBills");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("coin", "qc");
		params.put("dataType", "0");
		params.put("pageIndex", "1");
		params.put("pageSize", "10");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String transferInLever() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "transferInLever");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("coin", "qc");
		params.put("marketName", "btsqc");
		params.put("amount", "1");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String loan() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "loan");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("coin", "qc");
		params.put("amount", "1");
		params.put("interestRateOfDay", "0.05");
		params.put("repaymentDay", "10");
		params.put("isLoop", "0");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String cancelLoan() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "cancelLoan");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("loanId", "1454545445");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String getLoanRecords() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getLoanRecords");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("loanId", "1545454415");
		params.put("marketName", "btsqc");
		params.put("status", "1");
		params.put("pageIndex", "1");
		params.put("pageSize", "10");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String repay() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "repay");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("loanRecordId", "1454545415");
		params.put("repayAmount", "1.5");
		params.put("repayType", "1");
		String json = this.getJsonPost(params);
		return json;
	}
	public String getRepayments() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getRepayments");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("loanRecordId", "1454545415");
		params.put("pageIndex", "1");
		params.put("pageSize", "10");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String getFinanceRecords() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getFinanceRecords");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("coin", "qc");
		params.put("pageIndex", "1");
		params.put("pageSize", "10");
		String json = this.getJsonPost(params);
		return json;
	}
	public String changeInvestMark() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "changeInvestMark");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("investMark", "1");
		params.put("loanRecordId", "154545,457457574");
		String json = this.getJsonPost(params);
		return json;
	}
	public String changeLoop() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "changeLoop");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("isLoop", "0");
		params.put("loanId", "1154545");
		String json = this.getJsonPost(params);
		return json;
	}
	
	public String transferOutLever() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "transferOutLever");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("coin", "qc");
		params.put("marketName", "btsqc");
		params.put("amount", "1");
		String json = this.getJsonPost(params);
		return json;
	}

	//
	/** 获取数字资产提现记录 */
	public String getWithdrawRecord() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getWithdrawRecord");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("currency", symbol.substring(0, symbol.indexOf("_")));
		params.put("pageIndex", "1");
		params.put("pageSize", "10");
		String json = this.getJsonPost(params);
		return json;
	}

	/** 获取数字资产充值记录 */
	public String getChargeRecord() {
		Map<String, String> params = new TreeMap<String, String>();
		// 保持队形1.method,2.accesskey
		params.put("method", "getChargeRecord");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面
		params.put("currency", symbol.substring(0, symbol.indexOf("_")));
		params.put("pageIndex", "1");
		params.put("pageSize", "10");
		String json = this.getJsonPost(params);
		return json;
	}

	
	public void borrow() {
		Map<String, String> params = new LinkedHashMap<String, String>();
		// 保持队形1.method,2.accesskey,3.id,4.currency
		params.put("method", "borrow");
		params.put("accesskey", userApi.getApiKey());//
		// 这个需要加入签名,放前面
		params.put("marketName", "aeqc");
		params.put("coin", "qc");// 默认
		params.put("amount", "0.5");
		params.put("interestRateOfDay", "0.05");
		params.put("repaymentDay", "10");
		params.put("isLoop", "0");
		params.put("safePwd", "152433");
		
		
		
		String json = this.getJsonPost(params);
		System.out.println(json);
	}
	
	
	
	public void getLoans() {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("method", "getLoans");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("coin", "qc");
		params.put("pageIndex", "1");
		params.put("pageSize", "10");// 每页数量
		
		String json = getJsonPost(params);
		System.out.println(json);
	}

	/** 现价买入 */
	public TradeResult buy(BigDecimal price, BigDecimal amount) {

		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("method", "order");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("price", price.toPlainString());
		params.put("amount", amount.toPlainString());
		params.put("tradeType", "1");// 交易类型1/0[buy/sell]
		params.put("currency", symbol);// 交易类型(目前仅支持BTC/LTC/ETH)
//		params.put("acctType", "1");
		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "下单返回:" + json);
		return JSONObject.parseObject(json, TradeResult.class);
	}

	/** 现价卖出 */
	public TradeResult sell(BigDecimal price, BigDecimal amount) {
		TimeInterval timer = DateUtil.timer();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("method", "order");
		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		params.put("price", price.toPlainString());
		params.put("amount", amount.toPlainString());
		params.put("tradeType", "0");// 交易类型1/0[buy/sell]
		params.put("currency", symbol);// 交易类型(目前仅支持BTC/LTC/ETH)
		log.debug("下单参数:" + params.toString());
		String json = getJsonPost(params);
		log.debug("执行时间:" + timer.intervalMs() + "下单返回:" + json);
		return JSONObject.parseObject(json, TradeResult.class);
	}

	public double getTicker(String symbol) {
		//得到返回结果
		String returnJson = HttpRequest.get("http://api.zb.center/data/v1/ticker?market="+symbol).send().bodyText();
		Tickers ticker = JSONObject.parseObject(returnJson, Tickers.class);
		System.out.println("当前价格"+ticker.getTicker().getLast());
		return ticker.getTicker().getLast();
	}


	public void auto(String symbol){
		double currentAmount = getTicker(symbol);

//		TradeResult buyResult = apiPost.buy(currentAmount, 10000);
		//交易记录暂时用txt
		String record=symbol+"\t\t买入\t\t"+currentAmount+"\t\t"+10000+"\t\t"+new BigDecimal(currentAmount).multiply(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP).toString()+"\t\t"+DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)+"\n";
//		CaculateUtil.saveAsFileWriter(record,"D:\\record.txt");

		while(true){
			double nextAmount = getTicker(symbol);
			if(nextAmount-currentAmount>5000){
				//模拟卖出
				String sellrecord=symbol+"\t\t卖出\t\t"+nextAmount+"\t\t"+10000+"\t\t"+new BigDecimal(nextAmount).multiply(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP).toString()+"\t\t"+DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)+"\t\t"+(nextAmount-currentAmount)*10000;
//				CaculateUtil.saveAsFileWriter(sellrecord,"D:\\record.txt");
				break;
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("完成");
	}














	public void getOrdersIgnoreTradeType() {
		// 会抛出json{"code":3001,"message":"挂单没有找到"}
		Map<String, String> params = new LinkedHashMap<String, String>();
		// 保持队形1.method,2.accesskey,3.id,4.currency
		params.put("method", "getOrdersIgnoreTradeType");
		params.put("accesskey", userApi.getApiKey());//
		// 这个需要加入签名,放前面
		params.put("currency", symbol);
		params.put("pageIndex", Convert.toStr(1));
		params.put("pageSize", "10");// 每页数量
		log.debug("参数:" + params);
		String json = getJsonPost(params);
		System.out.println(json);
	}



	/**
	 * 获取json内容
	 * 
	 * @param params
	 * @return
	 */
	private String getJsonPost(Map<String, String> params) {

		params.put("accesskey", userApi.getApiKey());// 这个需要加入签名,放前面

		String digest = EncryDigestUtil.digest(userApi.getSecretKey());

		String sign = EncryDigestUtil.hmacSign(toStringMap(params), digest);

		String method = params.get("method");

		// 加入验证
		params.put("sign", sign);
		params.put("reqTime", Convert.toStr(System.currentTimeMillis()));

		String finalUrl = url + method + "?" + this.buildMap(params);
		CloseableHttpClient httpClient = HttpClients.createDefault();// 创建httpClient实例
		HttpGet httpGet= null; // 创建httpget实例
		CloseableHttpResponse response=null;
		String responseStr="";
		try {
			httpClient = HttpClients.createDefault();
			httpGet = new HttpGet(finalUrl);
			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
			response = httpClient.execute(httpGet);// 执行http get请求
			HttpEntity entity=response.getEntity(); // 获取返回实体
			responseStr=EntityUtils.toString(entity, "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close(); // response关闭
				httpClient.close(); // httpClient关闭
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responseStr;
	}

	public String buildMap(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (map.size() > 0) {
			for (String key : map.keySet()) {
				sb.append(key + "=");
				if (StringUtils.isEmpty(map.get(key))) {
					sb.append("&");
				} else {
					String value = map.get(key);
					try {
						value = URLEncoder.encode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					sb.append(value + "&");
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String params = "method=order&accesskey=f5f08725-6a14-4f95-a9cb-25f2299dff2f&price=10&amount=0.01&tradeType=1&currency=etc_cny";
		String secretkey = "1be5ac67-94ce-4f8c-8c47-e73ed5242f25";
		String sign = EncryDigestUtil.hmacSign(params, EncryDigestUtil.digest(secretkey));
		log.debug(sign);
	}

	/**
	 * 将参数集转为字符串
	 * 
	 * @param params
	 * @return
	 */
	private String getParamsForStr(Map<String, String> params) {
		Iterator<String> it = params.keySet().iterator();
		String str = "";
		while (it.hasNext()) {
			String key = it.next();
			String value = params.get(key);
			str += key + "=" + value + "&";
		}
		str = StringUtil.substring(str, 0, str.length() - 1);
		return str;
	}

	public String toStringMap(Map m) {
		// 按map键首字母顺序进行排序
		m = MapSort.sortMapByKey(m);

		StringBuilder sbl = new StringBuilder();
		for (Iterator<Entry> i = m.entrySet().iterator(); i.hasNext();) {
			Entry e = i.next();
			Object o = e.getValue();
			String v = "";
			if (o == null) {
				v = "";
			} else if (o instanceof String[]) {
				String[] s = (String[]) o;
				if (s.length > 0) {
					v = s[0];
				}
			} else {
				v = o.toString();
			}
			if (!e.getKey().equals("sign") && !e.getKey().equals("reqTime") && !e.getKey().equals("tx")) {
				// try {
				// sbl.append("&").append(e.getKey()).append("=").append(URLEncoder.encode(v,
				// "utf-8"));
				// } catch (UnsupportedEncodingException e1) {
				// e1.printStackTrace();
				sbl.append("&").append(e.getKey()).append("=").append(v);
				// }
			}
		}
		String s = sbl.toString();
		if (s.length() > 0) {
			return s.substring(1);
		}
		return "";
	}
}
